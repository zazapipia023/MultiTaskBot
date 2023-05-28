package ru.zaza.multitaskbot.commands.impl;

import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

import java.util.List;

@Component
public class GetPeripheryCommand implements Command<Long> {

    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    public GetPeripheryCommand(PeripheryService peripheryService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        sendGetPeripheryMessage(chatId);
    }

    private void sendGetPeripheryMessage(Long chatId) {
        List<Periphery> peripheryList = peripheryService.findAll();

        StringBuilder periphery = new StringBuilder().append("Список периферии на складе:\n");
        StringBuilder repairingPeriphery = new StringBuilder().append("Список периферии в ремонте:\n");

        for (Periphery per:
             peripheryList) {
            if (!per.getIsRepairing())
                periphery.append(per.getName()).append(" ").append(per.getSerialNumber()).append("\n");
            else
                repairingPeriphery.append(per.getName()).append(" ").append(per.getSerialNumber()).append("\n");
        }

        telegramService.sendMessage(chatId, String.valueOf(periphery.append("\n").append(repairingPeriphery)));
    }
}
