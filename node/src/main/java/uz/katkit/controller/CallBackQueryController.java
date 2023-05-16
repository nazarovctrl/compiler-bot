package uz.katkit.controller;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.katkit.service.main.CallBackQueryService;
import uz.katkit.service.main.CompilerService;
import uz.katkit.service.main.LangVersionService;

@Component
public class CallBackQueryController {

    private final CallBackQueryService service;
    private final LangVersionService langVersionService;
    private final CompilerService compilerService;

    public CallBackQueryController(CallBackQueryService service, LangVersionService langVersionService,  CompilerService compilerService) {
        this.service = service;
        this.langVersionService = langVersionService;
        this.compilerService = compilerService;
    }


    public void handle(CallbackQuery callbackQuery) {

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
        compilerService.runAndSendResult(split, message);


    }
}
