package uz.katkit.service.main;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.katkit.service.iproducer.IProducerService;

@Service
public class CallBackQueryService {

    private final LanguageService languageService;

    private final IProducerService producerService;

    public CallBackQueryService(LanguageService languageService, IProducerService producerService) {
        this.languageService = languageService;
        this.producerService = producerService;
    }

    public void backToLang(Message message) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());
        producerService.producerDelete(deleteMessage);

        languageService.chooseLang(message);
    }



}
