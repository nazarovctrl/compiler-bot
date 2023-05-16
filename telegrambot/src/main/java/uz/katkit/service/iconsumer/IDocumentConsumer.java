package uz.katkit.service.iconsumer;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import uz.katkit.dto.DocumentMessageDTO;

public interface IDocumentConsumer {
    void consumeDocument(DocumentMessageDTO documentMessage);
}
