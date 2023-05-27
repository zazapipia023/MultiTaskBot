package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

import java.util.List;

@Component
public class GetRepPeripheryCommand implements Command<Long> {

    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    @Autowired
    public GetRepPeripheryCommand(PeripheryService peripheryService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        sendGetRepPeripheryMessage(chatId);
    }

    private void sendGetRepPeripheryMessage(Long chatId) {
        List<Periphery> peripheryList = peripheryService.findAll();

        StringBuilder sb = new StringBuilder().append("Список периферии в ремонте:\n");

        for (Periphery per:
                peripheryList) {
            if (per.getIsRepairing())
                sb.append(per.getName()).append(" ").append(per.getSerialNumber()).append("\n");
        }

        telegramService.sendMessage(chatId, sb.toString());
    }
}
