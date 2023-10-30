package com.Hacakthon.demo.api.favor;

import com.Hacakthon.demo.api.chatty.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavorService {
    private final ChatGptService chatgptService;

    @Autowired
    public FavorService(ChatGptService chatgptService) {
        this.chatgptService = chatgptService;
    }


}
