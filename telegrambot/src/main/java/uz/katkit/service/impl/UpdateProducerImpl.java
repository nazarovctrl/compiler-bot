package uz.katkit.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.katkit.service.iproducer.IUpdateProducer;

@Slf4j
@Service
public class UpdateProducerImpl implements IUpdateProducer {
    private final RabbitTemplate rabbitTemplate;

    public UpdateProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void produce(String rabbitQueue, Update update) {

        log.debug(rabbitQueue + " " + update.toString());
        rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}
