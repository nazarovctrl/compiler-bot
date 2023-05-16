package uz.katkit.controller;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.polls.Poll;

import java.util.List;

@Component
public class MessageController {

    private final TextController textController;

    private final MediaController mediaController;

    public MessageController(TextController textController, MediaController mediaController) {
        this.textController = textController;
        this.mediaController = mediaController;
    }

    public void handle(Message message) {

        if (message.hasText()) {
            textController.handle(message);
            return;
        }


        if (message.hasVoice()) {
            Voice voice = message.getVoice();
        }

        if (message.hasAudio()) {
            Audio audio = message.getAudio();
        }

        if (message.hasVideo()) {
            Video video = message.getVideo();
        }

        if (message.hasDocument()) {
            Document document = message.getDocument();
        }

        if (message.hasAnimation()) {
            Animation animation = message.getAnimation();

        }

        if (message.hasPhoto()) {
            List<PhotoSize> photo = message.getPhoto();
        }

        if ((message.hasPoll())) {
            Poll poll = message.getPoll();
        }
    }
}
