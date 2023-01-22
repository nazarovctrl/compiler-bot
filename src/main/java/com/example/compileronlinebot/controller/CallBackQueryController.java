package com.example.compileronlinebot.controller;


import com.example.compileronlinebot.entity.RequestEntity;
import com.example.compileronlinebot.service.CallBackQueryService;
import com.example.compileronlinebot.service.CompilerService;
import com.example.compileronlinebot.service.LangVersionService;
import com.example.compileronlinebot.service.RequestService;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class CallBackQueryController {

    private final CallBackQueryService service;
    private final LangVersionService langVersionService;
    private final RequestService requestService;
    private final CompilerService compilerService;

    public CallBackQueryController(CallBackQueryService service, LangVersionService langVersionService, RequestService requestService, CompilerService compilerService) {
        this.service = service;
        this.langVersionService = langVersionService;
        this.requestService = requestService;
        this.compilerService = compilerService;
    }


    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        String data = callbackQuery.getData();
        String[] split = data.split("/");
        Message message = callbackQuery.getMessage();

        if ("back".equals(split[0])) {
            service.backToLang(message);
            return;
        }

        if (split.length == 1) {
            langVersionService.chooseLangVersion(split[0], message);
            return;
        }

        requestService.updateLangVersion(split[0], split[1], message.getChatId());
        RequestEntity lastRequest = requestService.getLastRequest(message.getChatId());

        compilerService.runAndSendResult(lastRequest,message);

    }
}
