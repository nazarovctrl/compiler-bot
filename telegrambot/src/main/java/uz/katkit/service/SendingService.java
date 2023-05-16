package uz.katkit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class SendingService {


    private TelegramBot telegramBot;

    public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(SendDocument sendDocument) {

        try {
            telegramBot.execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());

        }
    }

    public void sendMessage(SendMessage sendMessage) {



        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public void sendMessage(DeleteMessage deleteMessage) {
        try {
            telegramBot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
