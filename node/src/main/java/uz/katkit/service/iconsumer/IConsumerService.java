package uz.katkit.service.iconsumer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IConsumerService {
    void consumeTextMessageUpdates(Update update);

    void consumeCallbackMessageUpdates(Update update);

    void consumeMyChatMemberMessageUpdates(Update update);

}
