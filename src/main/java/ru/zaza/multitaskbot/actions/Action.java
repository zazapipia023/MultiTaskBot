package ru.zaza.multitaskbot.actions;

public interface Action {

    void execute(Long chatId, String text);

}
