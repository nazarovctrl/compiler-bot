package uz.katkit.service.iconsumer;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;

public interface IDeleteConsumer {
    void  consumeDelete(DeleteMessage deleteMessage);
}
