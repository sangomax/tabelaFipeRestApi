package br.com.agdeo.tabelafiperestapi.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public static String obterDados(String endereco) {
        // Cria um novo "HttpClient" com configuração padrão, equivalente ao newBuilder().build().
        // "HttpClient" pode ser usado para enviar "requests" e receber os "responses" deles.
        HttpClient client = HttpClient.newHttpClient();

        // URI: identificador uniforme de recurso
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
