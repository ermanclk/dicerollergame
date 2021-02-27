package org.familygame.service;

import org.familygame.util.ConfigurationReader;
import org.familygame.exception.FamilyGameException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RollDiceService implements RollDiceServiceI {

    HttpClient httpClient;
    ConfigurationReader config;

    public RollDiceService(HttpClient httpClient,ConfigurationReader configurationReader) {
        this.httpClient = httpClient;
        this.config = configurationReader;
    }

    public int callRemoteDiceRoller() throws InterruptedException {

        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(config.readConfig("remote.roll.dice.api")))
                    .build();

            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            throw new FamilyGameException("invalid application configuration, please check remote file url",e);
        }

        return getResult(response.body());
    }

    private static int getResult(String response) {
        return new JSONObject(response).getInt("score");
    }

}
