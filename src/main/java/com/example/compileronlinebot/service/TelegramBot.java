package com.example.compileronlinebot.service;

import com.example.compileronlinebot.config.BotConfig;
import com.example.compileronlinebot.controller.CallBackQueryController;
import com.example.compileronlinebot.controller.MessageController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final MessageController messageController;
    private final CallBackQueryController callBackQueryController;

    public TelegramBot(BotConfig botConfig, MessageController messageController, CallBackQueryController callBackQueryController) {
        this.botConfig = botConfig;
        this.messageController = messageController;
        this.callBackQueryController = callBackQueryController;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            messageController.handle(update.getMessage());
            return;
        }

        if (update.hasCallbackQuery()) {
            callBackQueryController.handle(update);
        }


    }

}
