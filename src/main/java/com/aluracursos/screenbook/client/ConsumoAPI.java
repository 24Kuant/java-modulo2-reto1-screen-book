package com.aluracursos.screenbook.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url) {
        HttpClient client = null;
        HttpResponse<String> response = null;
        try {
            client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException | RuntimeException e) {
            throw new RuntimeException(e);
        }

        return response.body();  //regresa el json de la respuesta
    }
}
