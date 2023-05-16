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
    public Queue AdminMessageQueue() {
        return new Queue(RabbitQueue.ADMIN_MESSAGE);
    }

}
