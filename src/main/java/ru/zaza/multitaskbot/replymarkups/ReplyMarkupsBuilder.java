package ru.zaza.multitaskbot.replymarkups;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

public final class ReplyMarkupsBuilder {

    private ReplyMarkupsBuilder() {}

    public static ReplyKeyboardMarkup createPeripheryListKeyboard() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text("Мышь TUF Gaming M3").build(),
                builder().text("Мышь ROG Gladius II Core").build()
        )));
        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text("Клавиатура TUF Gaming K7").build(),
                builder().text("Клавиатура TUF Gaming K3").build()
        )));
        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text("Клавиатура ROG Strix Flare").build(),
                builder().text("Наушники TUF Gaming H3").build()
        )));
        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text("Наушники ROG Headset").build()
        )));

        return keyboardBuilder.build();
    }

}
