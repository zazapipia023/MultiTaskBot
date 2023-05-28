package ru.zaza.multitaskbot.actions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class AddToRepairListAction implements Action {

    private final ClientService clientService;
    private final TelegramService telegramService;
    private final PeripheryService peripheryService;

    @Autowired
    public AddToRepairListAction(ClientService clientService, TelegramService telegramService, PeripheryService peripheryService) {
        this.clientService = clientService;
        this.telegramService = telegramService;
        this.peripheryService = peripheryService;
    }

    @Override
    public void execute(Long chatId, String text) {
        addToRepairList(chatId, text);
    }

    private void addToRepairList(Long chatId, String text) {
        Periphery periphery = peripheryService.findBySerialNumber(text);
        if (periphery == null) {
            sendAddToRepairListMessage(chatId, "Сначала добавьте периферию в список (/add_periphery)");
        } else {
            periphery.setIsRepairing(true);
            peripheryService.save(periphery);
            setAction(chatId);
            sendAddToRepairListMessage(chatId, "Периферия добавлена в список ремонтируемых");
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendAddToRepairListMessage(Long chatId, String message) {
        telegramService.sendMessage(chatId, message);
    }
}
