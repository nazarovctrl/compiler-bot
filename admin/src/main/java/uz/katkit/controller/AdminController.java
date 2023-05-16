package uz.katkit.controller;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminController {

    private final MessageController messageController;
    private final CallBackQueryController callBackQueryController;

    public AdminController(MessageController messageController, CallBackQueryController callBackQueryController) {
        this.messageController = messageController;
        this.callBackQueryController = callBackQueryController;
    }

    public void handle(Update update) {

        if (update.hasMessage()) {
            messageController.handle(update.getMessage());
            return;
        }


        if (update.hasCallbackQuery()) {
            callBackQueryController.handle(update.getCallbackQuery());
            return;
        }

    }
}
