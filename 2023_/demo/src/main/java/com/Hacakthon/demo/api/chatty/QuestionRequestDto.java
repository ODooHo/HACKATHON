package com.Hacakthon.demo.api.chatty;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDto implements Serializable {
    private String question;
}
