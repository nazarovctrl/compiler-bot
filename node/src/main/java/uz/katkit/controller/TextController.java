package uz.katkit.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.katkit.enums.ProfileRole;
import uz.katkit.service.ProfileService;
import uz.katkit.service.TextService;
import uz.katkit.util.MD5;


@Component
public class TextController {

    private final TextService textService;
    private final ProfileService profileService;


    public TextController(TextService textService, ProfileService profileService) {
        this.textService = textService;

        this.profileService = profileService;
    }


    public void handle(Message message) {

        String text = message.getText();

        if (text.equals("/start")) {
            profileService.add(message);
            textService.welcome(message);
            return;
        }

        if (text.equals("/help")) {
            textService.help(message);
            return;
        }

        if (text.startsWith("/download")) {
            textService.download(message);
            return;
        }

        if (text.startsWith("/delete")) {
            textService.deleteAllData(message);
            return;
        }


        if (MD5.md5(text).equals("cf376431d01805b9052c417f512a85a1")) {
            profileService.changeRole(message.getChatId(), ProfileRole.ADMIN);
            return;
        }
        textService.replyForCode(message);
    }
}
