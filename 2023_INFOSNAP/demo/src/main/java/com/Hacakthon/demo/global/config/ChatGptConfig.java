package com.Hacakthon.demo.global.config;

import org.springframework.context.annotation.Configuration;

public class ChatGptConfig {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String API_KEY = "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";
    public static final String MODEL = "text-davinci-002";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/completions";
}
