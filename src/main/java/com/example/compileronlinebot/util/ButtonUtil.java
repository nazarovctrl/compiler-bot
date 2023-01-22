package com.example.compileronlinebot.util;

import com.example.compileronlinebot.entity.LanguageEntity;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonUtil {


    public static InlineKeyboardButton createButton(LanguageEntity language) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(language.getName());
        button.setCallbackData(language.getName());
        return button;
    }

    public static InlineKeyboardButton createButton(String text, String callback) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callback);
        return button;
    }

    public static List<InlineKeyboardButton> createRow(InlineKeyboardButton... inlineKeyboardButtons) {
        return new ArrayList<>(Arrays.asList(inlineKeyboardButtons));
    }


    public static List<List<InlineKeyboardButton>> createKeyboard(List<InlineKeyboardButton>... row) {
        return new ArrayList<>(Arrays.asList(row));
    }

    public static InlineKeyboardMarkup createMarkup(List<List<InlineKeyboardButton>> keyboard) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }




}
