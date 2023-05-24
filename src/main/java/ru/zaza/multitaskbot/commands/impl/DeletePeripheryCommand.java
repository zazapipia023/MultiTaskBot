package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class DeletePeripheryCommand implements Command<Long> {

    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    @Autowired
    public DeletePeripheryCommand(PeripheryService peripheryService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        sendDeletePeripheryMessage(chatId);
    }

    public void sendDeletePeripheryMessage(Long chatId) {
        String message = "Выберите серийный номер периферии, которую нужно удалить";
        telegramService.sendMessage(chatId, message);
    }
}
