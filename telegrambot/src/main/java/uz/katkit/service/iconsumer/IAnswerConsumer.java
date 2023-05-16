package uz.katkit.service.iconsumer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface IAnswerConsumer {
    void consumeAnswer(SendMessage sendMessage);
}
