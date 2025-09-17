package com.example.print.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.print.model.Usuario;
import com.example.print.util.UrlsAPI;

public class BuscaUsuarios 
{
    public List<Usuario> getUsuarios()
    {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Usuario[]> response =restTemplate.getForEntity(UrlsAPI.buscaUsuarios(),Usuario[].class);
        
        Usuario[] employees = response.getBody();

        List<Usuario> usuarios = Arrays.asList(employees);

        return usuarios;
    }

    public Usuario buscaPorEmail(String email)
    {
        RestTemplate restTemplate = new RestTemplate();

        Usuario response = restTemplate.getForObject(UrlsAPI.buscaEmail(email), Usuario.class)  ;

        return response;
    }
}
