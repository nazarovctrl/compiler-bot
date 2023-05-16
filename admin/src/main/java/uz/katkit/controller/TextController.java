package uz.katkit.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.katkit.service.PageService;
import uz.katkit.service.TextService;

@Component
public class TextController {

    private final TextService textService;
    private final PageService pageService;

    public TextController(TextService textService, PageService pageService) {
        this.textService = textService;
        this.pageService = pageService;
    }


    public void handle(Message message) {
        String text = message.getText();
        if (text.equals("/start")) {
            textService.welcome(message.getChatId());
            return;
        }

        if (text.equals("Users\uD83D\uDC64")) {
            pageService.usersPage(message.getChatId(),null, 0, 2);
            return;
        }

        if (text.equals("Requests\uD83D\uDCCA")) {
            textService.sendInfoRequests(message.getChatId());
            return;
        }

    }
}
