package com.example.print.service;

import java.io.IOException;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.print.model.Usuario;
import com.example.print.util.UrlsAPI;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CadastraUsuario
{
    public ResponseEntity<String> cadastraUsuario(Usuario usuario)
    {
        final String url = UrlsAPI.cadastraUsuario(usuario);

        ObjectMapper mapper = new ObjectMapper();

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) 
        {
            ClassicHttpRequest httpPost = ClassicRequestBuilder
                        .post(url)
                        .setEntity(mapper.writeValueAsString(usuario), ContentType.APPLICATION_JSON)
                        .build();

            httpclient.execute(httpPost, response -> 
            {
                final HttpEntity entity2 = response.getEntity();
                EntityUtils.consume(entity2);
                return response.getCode() + " " + response.getReasonPhrase();
            });

            return new ResponseEntity<>("Usuario  cadastrado", HttpStatus.OK);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }   
    
        return new ResponseEntity<>("Usuario nao cadastrado", HttpStatus.BAD_REQUEST);
    }
}
