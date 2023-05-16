package uz.katkit.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.katkit.dto.RequestShortInfoDTO;
import uz.katkit.service.iproducer.IProducerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextService {

    private final IProducerService iProducerService;
    private final RequestService requestService;


    public TextService(IProducerService iProducerService, RequestService requestService) {

        this.iProducerService = iProducerService;
        this.requestService = requestService;
    }

    public void welcome(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ishni boshlaymizmi?");
        sendMessage.setChatId(chatId);

        KeyboardButton userCount = new KeyboardButton("Users\uD83D\uDC64");
        KeyboardButton requestCount = new KeyboardButton("Requests\uD83D\uDCCA");

        KeyboardRow row = new KeyboardRow();
        row.add(userCount);
        row.add(requestCount);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        markup.setKeyboard(keyboard);


        sendMessage.setReplyMarkup(markup);


        iProducerService.producerAnswer(sendMessage);

    }

    public void sendInfoRequests(Long chatId) {
        List<RequestShortInfoDTO> languageStatistic = requestService.getLanguageStatistic();
        int requestCount = requestService.getRequestCount();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);

        StringBuilder text = new StringBuilder();
        text.append("\uD83D\uDCCA *All request count:* _").append(requestCount).append("_");

        languageStatistic.forEach(dto -> {
            text.append("\n\n\uD83C\uDF10*Language name:* _").append(dto.getLanguageName()).append("_")
                    .append("\n*count:* _").append(dto.getCount()).append("_");
        });

        sendMessage.setText(text.toString());
        iProducerService.producerAnswer(sendMessage);

    }
}
