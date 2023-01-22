package com.example.compileronlinebot.controller;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class MessageController  {
    private final TextController textController;


    public MessageController(TextController textController) {
        this.textController = textController;
    }


    public void handle(Message message) {

        if (message.hasText()) {
            textController.handle(message);
        }

    }


}
