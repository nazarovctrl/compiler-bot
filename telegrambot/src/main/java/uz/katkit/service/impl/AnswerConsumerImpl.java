package uz.katkit.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import uz.katkit.dto.DocumentMessageDTO;
import uz.katkit.rabbit.RabbitQueue;
import uz.katkit.service.iconsumer.IAnswerConsumer;
import uz.katkit.service.SendingService;
import uz.katkit.service.iconsumer.IDeleteConsumer;
import uz.katkit.service.iconsumer.IDocumentConsumer;

import java.io.File;


@Slf4j
@Service
public class AnswerConsumerImpl implements IAnswerConsumer, IDeleteConsumer, IDocumentConsumer {
    private final SendingService sendingService;

    public AnswerConsumerImpl(SendingService sendingService) {
        this.sendingService = sendingService;
    }

    @Override
    @RabbitListener(queues = RabbitQueue.ANSWER_MESSAGE)
    public void consumeAnswer(SendMessage sendMessage) {
        sendingService.sendMessage(sendMessage);
    }

    @Override
    @RabbitListener(queues = RabbitQueue.DELETE_MESSAGE)
    public void consumeDelete(DeleteMessage deleteMessage) {
        sendingService.sendMessage(deleteMessage);
    }


    @Override
    @RabbitListener(queues = RabbitQueue.DOCUMENT_MESSAGE)
    public void consumeDocument(DocumentMessageDTO documentMessage) {
        File file = new File(documentMessage.getPath());
        InputFile inputFile = new InputFile();
        inputFile.setMedia(file, documentMessage.getFileName());

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(documentMessage.getChatId());
        sendDocument.setDocument(inputFile);
        sendDocument.setCaption(documentMessage.getCaption());
        sendDocument.setReplyToMessageId(documentMessage.getReplyMessageId());

        sendingService.sendMessage(sendDocument);
    }
}
