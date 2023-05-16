package uz.katkit.service.main;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.katkit.entity.LangVersionEntity;
import uz.katkit.repository.LangVersionRepository;
import uz.katkit.service.iproducer.IProducerService;
import uz.katkit.util.ButtonUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class LangVersionService {

    private final LangVersionRepository repository;
    private final IProducerService producerService;


    public LangVersionService(LangVersionRepository repository, IProducerService producerService) {
        this.repository = repository;
        this.producerService = producerService;
    }

    private List<LangVersionEntity> getList(String languageName) {
        return repository.findByLanguage_NameOrderByIndex(languageName);
    }


    public void chooseLangVersion(String languageName, Message message) {
        List<LangVersionEntity> langVersionList = getList(languageName);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        int size = langVersionList.size();
        int index = 0;
        int rowCount = size / 3;
        int lastRow = size % 3;

        if (size > 2) {
            for (int i = 0; i < rowCount; i++) {
                List<InlineKeyboardButton> row = createRow(index, 3, langVersionList);
                index += row.size();
                keyboard.add(row);
            }
        }

        keyboard.add(createRow(index, lastRow, langVersionList));
        keyboard.add(ButtonUtil.createRow(ButtonUtil.createButton("ortga", "back")));

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(message.getChatId());
        deleteMessage.setMessageId(message.getMessageId());

        producerService.producerDelete(deleteMessage);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Which version"+languageName+" do you want to run this code as?6");
        sendMessage.setReplyMarkup(ButtonUtil.createMarkup(keyboard));

        producerService.producerAnswer(sendMessage);
    }

    private List<InlineKeyboardButton> createRow(int index, int count, List<LangVersionEntity> langVersionList) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            LangVersionEntity langVersion = langVersionList.get(index++);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(langVersion.getName());
            button.setCallbackData(langVersion.getLanguage().getName() + "/" + langVersion.getIndex());

            row.add(button);
        }
        return row;

    }
}
