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
public class DeletePeripheryAction implements Action {

    private final PeripheryService peripheryService;
    private final ClientService clientService;
    private final TelegramService telegramService;

    @Autowired
    public DeletePeripheryAction(PeripheryService peripheryService, ClientService clientService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.clientService = clientService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId, String text) {
        deletePeriphery(chatId, text);
    }

    private void deletePeriphery(Long chatId, String serialNumber) {
        Periphery periphery = peripheryService.findBySerialNumber(serialNumber);
        if (periphery == null) sendDeletePeripheryMessage(chatId, "Серийный номер не найден");
        else {
            peripheryService.delete(periphery);
            Client client = clientService.findOne(chatId);
            client.setAction("none");
            clientService.save(client);
            sendDeletePeripheryMessage(chatId, "Периферия удалена");
        }
    }

    private void sendDeletePeripheryMessage(Long chatId, String message) {
        telegramService.sendMessage(chatId, message);
    }
}
