package com.example.print.util;

import com.example.print.model.Usuario;

public class UrlsAPI 
{   
    public static String buscaUsuarios()
	{
    	StringBuilder urlParaChamada = new StringBuilder()
				.append("http://localhost:8080/usuarios");

    	return urlParaChamada.toString();
	}

    public static String buscaEmail(String email)
	{
    	StringBuilder urlParaChamada = new StringBuilder()
				.append("http://localhost:8080/logar?")
                .append("login=")
                .append(email);

    	return urlParaChamada.toString();
	}  

	public static String cadastraUsuario(Usuario usuario)
	{
		StringBuilder urlParaChamada = new StringBuilder();	
        
		urlParaChamada.append("http://localhost:8080/cadastrar");

		return urlParaChamada.toString();
	}
}
