package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.commands.Commands;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class StartCommand implements Command<Long> {

    private final TelegramService telegramService;

    @Autowired
    public StartCommand(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        sendStartMessage(chatId);
    }

    private void sendStartMessage(Long chatId) {
        String message = "/add_periphery - добавить перефирию на склад\n" +
                "/remove_periphery - убрать периферию со склада\n";
        telegramService.sendMessage(chatId, message);
    }
}
