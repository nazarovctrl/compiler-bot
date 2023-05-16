package uz.katkit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.katkit.rabbit.RabbitQueue;


@Configuration
public class RabbitConfiguration {
    @Bean
    public MessageConverter jsonMessageConvertor() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(RabbitQueue.ANSWER_MESSAGE);
    }

    @Bean
    public Queue deleteMessageQueue() {
        return new Queue(RabbitQueue.DELETE_MESSAGE);
    }

    @Bean
    public Queue documentMessageQueue() {
        return new Queue(RabbitQueue.DOCUMENT_MESSAGE);
    }


}
