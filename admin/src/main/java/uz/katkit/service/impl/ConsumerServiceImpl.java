package uz.katkit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.katkit.rabbit.RabbitQueue;
import uz.katkit.controller.AdminController;
import uz.katkit.service.iconsumer.IConsumerService;

@Service
@Slf4j
public class ConsumerServiceImpl implements IConsumerService {

    private final AdminController adminController;

    public ConsumerServiceImpl(AdminController adminController) {
        this.adminController = adminController;
    }

    @Override
    @RabbitListener(queues = RabbitQueue.ADMIN_MESSAGE)
    public void consumeAdminMessageUpdates(Update update) {
        log.debug("");
        adminController.handle(update);
    }


}
