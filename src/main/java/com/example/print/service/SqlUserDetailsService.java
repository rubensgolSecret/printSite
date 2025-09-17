package com.example.print.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.print.model.Usuario;

@Service("SqlUserDetailsService")
public class SqlUserDetailsService implements UserDetailsService
{

    BuscaUsuarios bc = new BuscaUsuarios();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
       
        Usuario usuario = bc.buscaPorEmail(username);

        if(usuario != null)
            return usuario;

        throw new UsernameNotFoundException("Invalid user with username: "+ username);
    }

}
