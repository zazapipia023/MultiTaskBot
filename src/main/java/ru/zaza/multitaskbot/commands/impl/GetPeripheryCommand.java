package ru.zaza.multitaskbot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.replymarkups.ReplyMarkupsBuilder;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.senders.TelegramSender;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPeripheryCommand implements Command<Long> {

    private final PeripheryService peripheryService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId) {
        sendGetPeripheryMessage(chatId);
    }

    private void sendGetPeripheryMessage(Long chatId) {
        List<Periphery> peripheryList = peripheryService.findAll();

        StringBuilder periphery = new StringBuilder().append("Список периферии на складе:\n\n");
        StringBuilder repairingPeriphery = new StringBuilder().append("Список периферии в ремонте:\n");

        for (Periphery per : peripheryList) {
            if (!per.getIsRepairing())
                periphery.append(per.getName())
                        .append("\nS/N: ").append(per.getSerialNumber())
                        .append("\nПримечание: ").append(per.getDescription()).append("\n\n");
            else
                repairingPeriphery.append(per.getName())
                        .append("\nS/N: ").append(per.getSerialNumber())
                        .append("\nПримечание: ").append(per.getDescription()).append("\n\n");
        }

        telegramSender.sendMessage(chatId,
                String.valueOf(periphery.append("\n").append(repairingPeriphery)),
                ReplyMarkupsBuilder.createGetPeripheryKeyboard());
    }
}
