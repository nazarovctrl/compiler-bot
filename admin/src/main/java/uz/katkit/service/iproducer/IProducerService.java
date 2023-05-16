package uz.katkit.service.iproducer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

public interface IProducerService {
    void producerAnswer(SendMessage sendMessage);

    void producerAnswer(DeleteMessage deleteMessage);

}
