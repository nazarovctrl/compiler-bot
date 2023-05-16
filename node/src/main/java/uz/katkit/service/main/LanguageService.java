package uz.katkit.service.main;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.katkit.entity.LanguageEntity;
import uz.katkit.repository.LanguageRepository;
import uz.katkit.service.iproducer.IProducerService;
import uz.katkit.util.ButtonUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {
    private final LanguageRepository repository;

    private final IProducerService producerService;

    public LanguageService(LanguageRepository repository, IProducerService producerService) {
        this.repository = repository;
        this.producerService = producerService;
    }


    private List<LanguageEntity> getList() {
        Iterable<LanguageEntity> iterable = repository.findAll();
        List<LanguageEntity> languageList = new ArrayList<>();
        iterable.forEach(languageList::add);
        return languageList;
    }


    public void chooseLang(Message message) {
        List<LanguageEntity> languageList = getList();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        int size = languageList.size();
        int index = 0;
        int rowCount = size / 4;
        int lastRow = size % 4;

        if (size > 3) {
            for (int i = 0; i < rowCount; i++) {
                List<InlineKeyboardButton> row = createRow(index, 4, languageList);
                index += row.size();
                keyboard.add(row);
            }
        }
        keyboard.add(createRow(index, lastRow, languageList));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Which language do you want to run this code as?");
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(ButtonUtil.createMarkup(keyboard));

        producerService.producerAnswer(sendMessage);
    }

    public List<InlineKeyboardButton> createRow(int index, int count, List<LanguageEntity> languageList) {

        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            row.add(ButtonUtil.createButton(languageList.get(index++)));
        }
        return row;
    }

}
