package uz.katkit.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.katkit.service.PageService;

@Component
public class CallBackQueryController {


    private final PageService pageService;

    public CallBackQueryController(PageService pageService) {

        this.pageService = pageService;
    }

    public void handle(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        String[] split = data.split("/");

        if ("user".equals(split[0])) {
            int currentPage = Integer.parseInt(split[2]);
            if ("next".equals(split[1])) {
                ++currentPage;
            } else if ("back".equals(split[1])) {
                --currentPage;
            }
            pageService.usersPage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId(), currentPage, 2);
            return;
        }

    }
}
