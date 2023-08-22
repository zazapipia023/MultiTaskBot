package ru.zaza.multitaskbot.commands.impl;

import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.PeripheryService;

import java.util.List;

@Component
public class SendAllPeripheryCommand implements Command<Long> {

    private final PeripheryService peripheryService;
    private final TelegramSender telegramSender;

    public SendAllPeripheryCommand(PeripheryService peripheryService, TelegramSender telegramSender) {
        this.peripheryService = peripheryService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        List<Periphery> peripheryList = peripheryService.findAllRepairing();
        if (peripheryList.isEmpty()) {
            sendAllPeripheryMessage(chatId, "Список периферии, которую нужно отдать в ремонт - пустой");
        } else {
            peripheryService.deleteAllRepairing();
            StringBuilder periphery = new StringBuilder().append("Периферия, которую забрали в ремонт:\n\n");
            for (Periphery per : peripheryList) {
                    periphery.append(per.getName())
                            .append("\nS/N: ").append(per.getSerialNumber())
                            .append("\nПримечание: ").append(per.getDescription()).append("\n\n");
            }

            sendAllPeripheryMessage(chatId, periphery.toString());
            sendAllPeripheryMessage(180802438L, periphery.toString());
        }
    }

    private void sendAllPeripheryMessage(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }
}
