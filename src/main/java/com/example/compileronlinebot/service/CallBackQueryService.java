package com.example.compileronlinebot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CallBackQueryService {

    private final LanguageService languageService;

    private final SendingService sendingService;

    public CallBackQueryService(LanguageService languageService, SendingService sendingService) {
        this.languageService = languageService;
        this.sendingService = sendingService;
    }

    public void backToLang(Message message) {
        languageService.chooseLang(message);
        sendingService.deleteMessage(message);
    }



}
