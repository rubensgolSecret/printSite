package com.example.print.controlers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.ui.Model;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.print.model.CadastrarRequest;
import com.example.print.model.LoginRequest;
import com.example.print.model.Usuario;
import com.example.print.service.BuscaUsuarios;
import com.example.print.service.CadastraUsuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class EndPointer
{
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	public EndPointer(AuthenticationManager authenticationManager) 
    {
		this.authenticationManager = authenticationManager;
	}

    @GetMapping("/login")
    public ModelAndView loginPage() 
    {
        return new ModelAndView("login.html");
    }

    @PostMapping("/login")
    public ModelAndView login(LoginRequest loginRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) 
    {
        SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();

        Authentication authenticationRequest =
            UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
        
        securityContext.setAuthentication(authenticationResponse);
        securityContextHolderStrategy.setContext(securityContext);
        SecurityContextHolder.setContext(securityContext);

        securityContextRepository.saveContext(securityContext, httpServletRequest, httpServletResponse);

        return new ModelAndView("home.html");
    }

    @GetMapping("/home")
    public ModelAndView home() 
    {
        return new ModelAndView("home.html");
    }

    @GetMapping("/preco")
    public ModelAndView preco() 
    {
        return new ModelAndView("preco.html");
    }

    @GetMapping("/")
    public ModelAndView padrao() 
    {
        return new ModelAndView("home.html");
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() 
    {
        return new ModelAndView("cadastrar.html");
    }


    @PostMapping("/cadastrarAcao")
    public ModelAndView cadastrarAcao(CadastrarRequest loginRequest) 
    {
        Usuario user = new Usuario();
        user.setEmail(loginRequest.getUsername());
        user.setNome(loginRequest.getNome());
        user.setSenha(loginRequest.getPassword());

        CadastraUsuario cu = new CadastraUsuario();
        ResponseEntity<String> retorno = cu.cadastraUsuario(user);

        if (retorno.getStatusCode() == HttpStatus.OK)
            return new ModelAndView("home.html");
        else
            return new ModelAndView("cadastrar.html");
    }

    @GetMapping("/test")
    public String getStyledPage(Model model) 
    {
        BuscaUsuarios b =  new BuscaUsuarios();

        List<Usuario> user = b.getUsuarios();

        return user.toString();
    }
}
