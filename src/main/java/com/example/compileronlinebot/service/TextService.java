package com.example.compileronlinebot.service;

import com.example.compileronlinebot.entity.LanguageEntity;
import com.example.compileronlinebot.util.ButtonUtil;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextService {
    private final SendingService sendingService;


    public TextService(SendingService sendingService) {
        this.sendingService = sendingService;
    }

    public void welcome(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getFrom().getFirstName() + " Botga xush kelibsiz");
        sendingService.sendMessage(sendMessage);
    }


}
