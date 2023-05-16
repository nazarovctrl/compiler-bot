package uz.katkit.controller;

import lombok.extern.slf4j.Slf4j;
import uz.katkit.rabbit.RabbitQueue;
import uz.katkit.repository.ProfileRepository;
import uz.katkit.service.SendingService;
import uz.katkit.service.iproducer.IUpdateProducer;
import uz.katkit.util.MessageUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;



@Component
@Slf4j
public class UpdateController {

    private final SendingService sendingService;
    private final MessageUtil messageUtil;
    private final IUpdateProducer updateProducer;

    private final ProfileRepository profileRepository;

    public UpdateController(SendingService sendingService, MessageUtil messageUtil, IUpdateProducer updateProducer, ProfileRepository profileRepository) {
        this.sendingService = sendingService;
        this.messageUtil = messageUtil;
        this.updateProducer = updateProducer;
        this.profileRepository = profileRepository;

    }


    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Received update is null");
            return;
        }
        distributeMessageByType(update);

    }


    public void distributeMessageByType(Update update) {

        Long chatId;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }else{chatId = update.getMessage().getChatId();
        }
        Long user = profileRepository.getAdminUserIdList().stream().filter(userId -> userId.equals(chatId)).findFirst().orElse(null);
        if (user != null) {
            processAdminMessage(update);
            return;
        }


        if (update.hasMyChatMember()) {
            processMyChartMember(update);
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            processTextMessage(update);
            return;
        }

        if (update.hasCallbackQuery()) {
            processCallBackQuery(update);
            return;
        }

        updateTypeNotSupport(update);

    }

    private void processMyChartMember(Update update) {
        updateProducer.produce(RabbitQueue.MY_CHAT_MEMBER_MESSAGE, update);
    }

    private void updateTypeNotSupport(Update update) {
        SendMessage message = messageUtil.generateSendMessage(update, "Message Not support");
        sendingService.sendMessage(message);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(RabbitQueue.TEXT_MESSAGE, update);
    }

    private void processCallBackQuery(Update update) {
        updateProducer.produce(RabbitQueue.CALLBACK_MESSAGE, update);
    }

    private void processAdminMessage(Update update) {
        updateProducer.produce(RabbitQueue.ADMIN_MESSAGE, update);
    }


}


