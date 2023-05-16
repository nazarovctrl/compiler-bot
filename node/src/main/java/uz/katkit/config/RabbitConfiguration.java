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
    public Queue textMessageQueue() {
        return new Queue(RabbitQueue.TEXT_MESSAGE);
    }

    @Bean
    public Queue callbackMessageQueue() {
        return new Queue(RabbitQueue.CALLBACK_MESSAGE);
    }

    @Bean
    public Queue myChatMemberMessageQueue() {
        return new Queue(RabbitQueue.MY_CHAT_MEMBER_MESSAGE);
    }


}
