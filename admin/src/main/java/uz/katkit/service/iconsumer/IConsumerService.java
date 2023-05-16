package uz.katkit.service.iconsumer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IConsumerService {
    void consumeAdminMessageUpdates(Update update);

}
