package com.example.compileronlinebot.controller;

import com.example.compileronlinebot.service.LanguageService;
import com.example.compileronlinebot.service.RequestService;
import com.example.compileronlinebot.service.TextService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class TextController  {

    private final TextService textService;
    private final RequestService requestService;
    private final LanguageService languageService;


    public TextController(TextService textService, RequestService requestService,
                          LanguageService languageService) {
        this.textService = textService;
        this.requestService = requestService;
        this.languageService = languageService;
    }


    public void handle(Message message) {
        String text = message.getText();

        if (text.equals("/start")) {
            textService.welcome(message);
            return;
        }
        requestService.addRequest(text, message.getChatId(), message.getMessageId());
        languageService.chooseLang(message);
    }
}
