package uz.katkit.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageUtil {

    public SendMessage generateSendMessage(Update update ,String text){
        Message message = update.getMessage();
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        sendMessage.setReplyToMessageId(message.getMessageId());
        return sendMessage;
    }

}
