package com.Hacakthon.demo.api.chatty;

import com.Hacakthon.demo.api.chatty.Choice;

import java.util.List;
import java.util.Optional;

public record ChatGptResponse(List<Choice> choices) {

    public Optional<String> getText(){
        if(choices==null || choices.isEmpty())
            return Optional.empty();
        return Optional.of(choices.get(0).text);
    }

    record Choice(String text) {}
}