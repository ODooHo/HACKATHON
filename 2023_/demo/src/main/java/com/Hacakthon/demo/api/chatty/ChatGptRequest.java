package com.Hacakthon.demo.api.chatty;

public record ChatGptRequest(String model, String prompt, double temperature, int max_tokens) {
    public static ChatGptRequest create(String prompt){
        return new ChatGptRequest("text-davinci-003",prompt,1,100);
    }
}