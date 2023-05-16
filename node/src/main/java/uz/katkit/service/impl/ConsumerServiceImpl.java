package uz.katkit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.katkit.rabbit.RabbitQueue;
import uz.katkit.controller.CallBackQueryController;
import uz.katkit.controller.MyChatMemberController;
import uz.katkit.controller.TextController;
import uz.katkit.service.iconsumer.IConsumerService;

@Service
@Slf4j
public class ConsumerServiceImpl implements IConsumerService {
    private final TextController textController;
    private final CallBackQueryController callBackQueryController;
    private final MyChatMemberController myChatMemberController;

    public ConsumerServiceImpl(TextController textController, CallBackQueryController callBackQueryController, MyChatMemberController myChatMemberController) {
        this.textController = textController;
        this.callBackQueryController = callBackQueryController;
        this.myChatMemberController = myChatMemberController;
    }

    @Override
    @RabbitListener(queues = RabbitQueue.TEXT_MESSAGE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE : Text message is received");
        textController.handle(update.getMessage());
    }


    @Override
    @RabbitListener(queues = RabbitQueue.CALLBACK_MESSAGE)
    public void consumeCallbackMessageUpdates(Update update) {
        log.debug("NODE : callback message is received");
        callBackQueryController.handle(update.getCallbackQuery());
    }

    @Override
    @RabbitListener(queues = RabbitQueue.MY_CHAT_MEMBER_MESSAGE)
    public void consumeMyChatMemberMessageUpdates(Update update) {
        log.debug("");
        myChatMemberController.handle(update.getMyChatMember());
    }


}
