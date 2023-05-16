package uz.katkit.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.katkit.entity.ProfileEntity;
import uz.katkit.service.iproducer.IProducerService;
import uz.katkit.util.ButtonUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {

    private final ProfileService profileService;
    private final IProducerService iProducerService;

    public PageService(ProfileService profileService, IProducerService iProducerService) {
        this.profileService = profileService;
        this.iProducerService = iProducerService;
    }


    public void usersPage(Long chatId, Integer messageId, int page, int size) {


        Page<ProfileEntity> pageList = profileService.getPageList(page, size);

        if (page < 0 || page > pageList.getTotalPages() - 1 || size < 1) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Xatolik");
            iProducerService.producerAnswer(sendMessage);
            return;
        }

        String text = getUserPageText(pageList, page);


        List<InlineKeyboardButton> row = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);

        InlineKeyboardButton back = ButtonUtil.buttonCreate("<<<<", "user/back/" + page);
        InlineKeyboardButton next = ButtonUtil.buttonCreate(">>>>", "user/next/" + page);
        InlineKeyboardButton button = ButtonUtil.buttonCreate(String.valueOf(page), "user/page/" + page);


        if (page > 0) {
            row.add(back);
        }
        row.add(button);

        if (page < pageList.getTotalPages() - 1) {
            row.add(next);
        }

        if (messageId != null) {
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setMessageId(messageId);
            deleteMessage.setChatId(chatId);
            iProducerService.producerAnswer(deleteMessage);
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(markup);
        iProducerService.producerAnswer(sendMessage);
    }


    private String getUserPageText(Page<ProfileEntity> pageList, int page) {
        long totalElements = pageList.getTotalElements();
        int totalPages = pageList.getTotalPages();

        StringBuilder text = new StringBuilder("\uD83D\uDC65 *Users:* _" + totalElements + "_");

        List<ProfileEntity> content = pageList.getContent();
        content.forEach(profile -> {
            text.append("\n\n \uD83C\uDD94 *Id:* _").append(profile.getId()).append("_")
                    .append("\n *Full name :* _").append(profile.getFullName()).append("_")
                    .append("\n *Username :* _");
            if (profile.getUsername() != null) {
                text.append("@");
            }
            text.append(profile.getUsername()).append("_")
                    .append("\n\uD83E\uDEAA*UserId*: _").append(profile.getUserId()).append("_")
                    .append("\n *Role :* _").append(profile.getRole()).append("_")
                    .append("\n\uD83C\uDFF3*LanCode:* _").append(profile.getLanguageCode()).append("_")
                    .append("\n\uD83D\uDCA1*Visible:* _").append(profile.getVisible()).append("_");
        });

        text.append("\nTotal pages: ").append(totalPages);
        text.append("\nCurrent page : ").append(page);

        return text.toString();
    }


}
