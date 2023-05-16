package uz.katkit.service.iproducer;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import uz.katkit.dto.DocumentMessageDTO;

public interface IProducerService {
    void producerAnswer(SendMessage sendMessage);

    void producerDelete(DeleteMessage deleteMessage);


    void producerDocument(DocumentMessageDTO documentMessage);
}
