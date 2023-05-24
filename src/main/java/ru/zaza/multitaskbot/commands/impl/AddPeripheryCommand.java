package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.replymarkups.ReplyMarkupsBuilder;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class AddPeripheryCommand implements Command<Long> {

    private final TelegramService telegramService;

    @Autowired
    public AddPeripheryCommand(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        sendAddPeripheryMessage(chatId);
    }

    public void sendAddPeripheryMessage(Long chatId) {
        String message = "Какую периферию вы хотите добавить?";
        telegramService.sendMessage(chatId, message, ReplyMarkupsBuilder.createPeripheryListKeyboard());
    }
}
