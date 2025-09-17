package com.example.print.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarRequest
{
    public CadastrarRequest(String nome, String username, String password)
    {
        this.nome = nome;
        this.username = username;
        this.password = password;
    }

    private String nome;
    private String username;
    private String password;
}
