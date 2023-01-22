package com.example.compileronlinebot.service;

import com.example.compileronlinebot.entity.RequestEntity;
import com.example.compileronlinebot.util.Compiler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class CompilerService {

    private final Compiler compiler;

    private final SendingService sendingService;

    public CompilerService(Compiler compiler, SendingService sendingService) {
        this.compiler = compiler;
        this.sendingService = sendingService;
    }


    public void runAndSendResult(RequestEntity request, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Running");
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(request.getMessageId());
        Message waitMessage = sendingService.sendMessage(sendMessage);


        sendingService.deleteMessage(message);

        String result = compiler.compiler(
                request.getCode(),
                request.getLangVersion().getLanguage().getName(),
                String.valueOf(request.getLangVersion().getIndex())
        );

        SendMessage messageResult = new SendMessage();
        messageResult.setText(result);
        messageResult.setChatId(request.getUserId());
        messageResult.setReplyToMessageId(request.getMessageId());
        sendingService.sendMessage(messageResult);

        sendingService.deleteMessage(waitMessage);

    }


}
