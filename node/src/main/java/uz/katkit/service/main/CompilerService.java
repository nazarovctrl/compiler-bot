package uz.katkit.service.main;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.katkit.entity.RequestEntity;
import uz.katkit.service.iproducer.IProducerService;
import uz.katkit.util.Compiler;


@Service
public class CompilerService {

    private final Compiler compiler;
    private final IProducerService producerService;
    private final RequestService requestService;

    public CompilerService(Compiler compiler, IProducerService producerService, RequestService requestService) {
        this.compiler = compiler;
        this.producerService = producerService;
        this.requestService = requestService;
    }


    public void runAndSendResult(String[] split, Message message) {
        requestService.updateLangVersion(split[0], split[1], message.getChatId());
        RequestEntity lastRequest = requestService.getLastRequest(message.getChatId());


        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());
        producerService.producerDelete(deleteMessage);


        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Running");
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(lastRequest.getMessageId());

        producerService.producerAnswer(sendMessage);




        String result = compiler.compiler(
                lastRequest.getCode(),
                lastRequest.getLangVersion().getLanguage().getName(),
                String.valueOf(lastRequest.getLangVersion().getIndex())
        );

        requestService.saveResult(result, lastRequest.getId());


        SendMessage messageResult = new SendMessage();
        messageResult.setText(result);
        messageResult.setChatId(lastRequest.getUserId());
        messageResult.setReplyToMessageId(lastRequest.getMessageId());

//        DeleteMessage deleteWaitMessage = new DeleteMessage();
//        deleteMessage.setChatId(waitMessage.getChatId());
//        deleteMessage.setMessageId(waitMessage.getMessageId());
//        producerService.producerDelete(deleteWaitMessage);

        producerService.producerAnswer(messageResult);


    }


}
