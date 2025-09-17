package com.example.print.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Usuario implements UserDetails
{
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Role> roles;

    public Usuario() 
    {

    }

    public Usuario(String nome, String email) 
    {
        super();
        this.nome = nome;
        this.email = email;
    }

    public Usuario(Usuario usuario) 
    {
        super();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.roles = usuario.getRoles();
        this.id = usuario.getId();
    }

    public Usuario(String nome, String email, String senha, List<Role> roles) 
    {
        super();
        this.nome = nome;
        this.email = email;
        this.roles = roles;
        this.senha = senha;
    }
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public String getSenha() 
    {   
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        return encoder.encode(senha);
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() 
    {
        return List.of( new SimpleGrantedAuthority("ROLE_USER") );
    }

    @Override
    public String getPassword() 
    {
        return getSenha();
    }

    @Override
    public String getUsername() 
    {
       return getEmail();
    }
}
