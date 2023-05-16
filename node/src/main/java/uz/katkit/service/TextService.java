package uz.katkit.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.katkit.dto.DocumentMessageDTO;
import uz.katkit.dto.RequestShortInfoDTO;
import uz.katkit.service.iproducer.IProducerService;
import uz.katkit.service.main.LanguageService;
import uz.katkit.service.main.RequestService;
import uz.katkit.util.ExcelUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TextService {
    private final IProducerService producerService;
    private final RequestService requestService;
    private final LanguageService languageService;

    public TextService(IProducerService producerService, RequestService requestService, LanguageService languageService) {
        this.producerService = producerService;
        this.requestService = requestService;
        this.languageService = languageService;
    }


    public LocalDate getLocalDate(String date, String errorText, Message message) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        } catch (RuntimeException e) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setText(errorText);
            producerService.producerAnswer(sendMessage);
            return null;
        }

    }

    public void deleteAllData(Message message) {
        int count;
        String[] strings = message.getText().split(" ");
        if (strings.length > 1) {
            String errorText = """
                    ‼️For download by date 📅
                    enter the date as in the example
                    yyyy.MM.dd""";

            LocalDate localDate = getLocalDate(strings[1], errorText, message);
            if (localDate == null) {
                return;
            }

            count = requestService.deleteAllRequests(message.getChatId(), localDate);
        } else {
            count = requestService.deleteAllRequests(message.getChatId());
        }


        if (!check(count, message, "‼️Nothing to delete")) {
            return;
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("✅ All data deleted");
        sendMessage.setReplyToMessageId(message.getMessageId());
        producerService.producerAnswer(sendMessage);
    }

    public boolean check(int number, Message message, String text) {
        if (number != 0) {
            return true;
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        producerService.producerAnswer(sendMessage);
        return false;

    }

    public void welcome(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getFrom().getFirstName() + " Botga xush kelibsiz");
        producerService.producerAnswer(sendMessage);
    }

    public void download(Message message) {

        List<RequestShortInfoDTO> allRequests;

        String[] strings = message.getText().split(" ");
        if (strings.length > 1) {
            String errorText = """
                    ‼️For download by date 📅
                    enter the date as in the example
                    yyyy.MM.dd""";

            LocalDate localDate = getLocalDate(strings[1], errorText, message);
            if (localDate == null) {
                return;
            }
            allRequests = requestService.getAllRequests(message.getChatId(), localDate);
        } else {
            allRequests = requestService.getAllRequests(message.getChatId());
        }

        if (check(allRequests.size(), message, "‼️Nothing to send")) {
            File file = ExcelUtil.createExcel(allRequests, message.getChatId());
            DocumentMessageDTO messageDTO = new DocumentMessageDTO("Data", file.getName(), file.getPath(), message.getChatId(), message.getMessageId());
            producerService.producerDocument(messageDTO);
        }


    }

    public void replyForCode(Message message) {
        requestService.addRequest(message.getText(), message.getChatId(), message.getMessageId());
        languageService.chooseLang(message);
    }

    public void help(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("""
                💡 Hello ! I'm Compiler bot \uD83E\uDD16 \s
                I was made by @Nazarov22222 \uD83E\uDDD1\uD83C\uDFFC\u200D\uD83D\uDCBB\s
                For more information write \uD83D\uDCDD  to my owner\s""");
        sendMessage.setReplyToMessageId(message.getMessageId());
        producerService.producerAnswer(sendMessage);
    }
}
