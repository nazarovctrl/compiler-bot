package uz.katkit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.katkit.entity.ProfileEntity;
import uz.katkit.enums.ProfileRole;
import uz.katkit.repository.ProfileRepository;
import uz.katkit.service.iproducer.IProducerService;

@Slf4j
@Service
public class ProfileService {
    private final ProfileRepository repository;
    private final IProducerService producerService;

    public ProfileService(ProfileRepository repository, IProducerService producerService) {
        this.repository = repository;
        this.producerService = producerService;
    }


    private boolean exists(Long userId) {
        return repository.existsByUserId(userId);
    }

    public void add(Message message) {
        if (exists(message.getChatId())) {
            return;
        }

        User profile = message.getFrom();
        ProfileEntity entity = new ProfileEntity();
        entity.setUserId(message.getChatId());
        entity.setFullName(profile.getFirstName() + " " + profile.getLastName());
        entity.setUsername(profile.getUserName());
        entity.setLanguageCode(profile.getLanguageCode());
        entity.setIsPremium(profile.getIsPremium());


        repository.save(entity);
        log.debug("Profile created " + entity);
    }


    public void changeVisibleByUserId(Long userId, boolean b) {
        repository.changeVisibleBuUserId(userId, b);
    }

    public void changeRole(Long chatId, ProfileRole admin) {
        repository.changeRole(chatId, admin);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("You haven promoted to ADMIN ");
        producerService.producerAnswer(sendMessage);
    }
}

