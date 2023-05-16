package uz.katkit.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import uz.katkit.rabbit.RabbitQueue;
import uz.katkit.service.iproducer.IProducerService;

@Service
public class ProducerServiceImpl implements IProducerService {

    private final RabbitTemplate rabbitTemplate;

    public ProducerServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void producerAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(RabbitQueue.ANSWER_MESSAGE, sendMessage);
    }

    @Override
    public void producerAnswer(DeleteMessage deleteMessage) {
        rabbitTemplate.convertAndSend(RabbitQueue.DELETE_MESSAGE, deleteMessage);
    }

}
