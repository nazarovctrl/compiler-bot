package uz.katkit.service.iproducer;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IUpdateProducer {
void produce(String rabbitQueue, Update update);
}
