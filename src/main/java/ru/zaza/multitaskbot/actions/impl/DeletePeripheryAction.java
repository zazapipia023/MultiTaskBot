package ru.zaza.multitaskbot.actions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class DeletePeripheryAction implements Action {

    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    @Autowired
    public DeletePeripheryAction(PeripheryService peripheryService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId, String text) {

    }
}
