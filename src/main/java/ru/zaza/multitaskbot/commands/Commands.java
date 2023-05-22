package ru.zaza.multitaskbot.commands;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;

public final class Commands {

    public static final String START_COMMAND = "/start";

    public static final String ADD_PERIPHERY_COMMAND = "/add_periphery";
    public static final String REMOVE_PERIPHERY_COMMAND = "/remove_periphery";

    private Commands() {}

    public static ReplyKeyboardMarkup createGeneralMenuKeyboard() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text(ADD_PERIPHERY_COMMAND).build(),
                builder().text(REMOVE_PERIPHERY_COMMAND).build()
        )));

        return keyboardBuilder.build();
    }
}
