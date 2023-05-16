package uz.katkit.controller;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import uz.katkit.service.ProfileService;
import uz.katkit.service.iproducer.IProducerService;

@Component
public class MyChatMemberController {

    private final ProfileService profileService;

    private final IProducerService iProducerService;

    public MyChatMemberController(ProfileService profileService, IProducerService iProducerService) {
        this.profileService = profileService;
        this.iProducerService = iProducerService;
    }

    public void handle(ChatMemberUpdated myChatMember) {
        ChatMember newChatMember = myChatMember.getNewChatMember();
        String status = newChatMember.getStatus();
        Long userId = myChatMember.getFrom().getId();


        if (status.equals("kicked")) {
            profileService.changeVisibleByUserId(userId, false);
            return;
        }

        if (status.equals("member")) {
            profileService.changeVisibleByUserId(userId, true);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Botni qayta ishga tushirganingizdan xursandmiz");
            sendMessage.setChatId(userId);
            iProducerService.producerAnswer(sendMessage);
        }
    }
}
