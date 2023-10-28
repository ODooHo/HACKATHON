package com.Hacakthon.demo.api.chatty;

import com.Hacakthon.demo.api.chatty.ChatGptService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatty")
public class ChatGptController {
    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("")
    public String index(){
        return "index";
    }


    @PostMapping("/question")
    public String chat(Model model, @ModelAttribute String prompt){
        try{
            model.addAttribute("request",prompt);
            model.addAttribute("response",chatGptService.chat(prompt));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "index";
    }
}