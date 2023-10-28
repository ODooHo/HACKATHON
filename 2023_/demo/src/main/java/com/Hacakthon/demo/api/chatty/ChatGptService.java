package com.Hacakthon.demo.api.chatty;

import com.Hacakthon.demo.global.config.ChatGptConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.*;

@Service
public class ChatGptService {

    private ObjectMapper objectMapper;

    private HttpClient httpClient;

    public String chat(String prompt) throws Exception{
        var chatGptRequest = ChatGptRequest.create(prompt);
        var requestBody = objectMapper.writeValueAsString(chatGptRequest);
        var responseBody = sendRequest(requestBody);

        var chatGptResponseReponse = objectMapper.readValue(responseBody,ChatGptResponse.class);

    return chatGptResponseReponse.getText().orElseThrow();
    }

    private String sendRequest(String requestBodyAsJson) throws Exception{
        String apiUrl = "https://api.openai.com/v1/completions";
        String openApiKey = "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";
        URI url = URI.create(apiUrl);
        var request = HttpRequest.newBuilder().uri(url)
                .header(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+ openApiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBodyAsJson)).build();
        return httpClient.send(request,HttpResponse.BodyHandlers.ofString()).body();
    }

}
