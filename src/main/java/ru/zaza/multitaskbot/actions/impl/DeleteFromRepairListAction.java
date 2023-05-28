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
public class DeleteFromRepairListAction implements Action {

    private final ClientService clientService;
    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    @Autowired
    public DeleteFromRepairListAction(ClientService clientService, PeripheryService peripheryService, TelegramService telegramService) {
        this.clientService = clientService;
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId, String text) {
        removeFromRepairList(chatId, text);
    }

    private void removeFromRepairList(Long chatId, String text) {
        Periphery periphery = peripheryService.findBySerialNumber(text);
        if (periphery == null || !periphery.getIsRepairing()) {
            sendDeleteFromRepairListMessage(chatId, "Нет периферии с таким серийным номером в ремонте");
        }
        else {
            periphery.setIsRepairing(false);
            peripheryService.save(periphery);
            setAction(chatId);
            sendDeleteFromRepairListMessage(chatId, "Периферия убрана из ремонта");
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendDeleteFromRepairListMessage(Long chatId, String message) {
        telegramService.sendMessage(chatId, message);
    }
}
