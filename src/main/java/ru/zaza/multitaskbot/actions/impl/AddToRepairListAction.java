package ru.zaza.multitaskbot.actions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.senders.TelegramSender;

import java.util.Arrays;
import java.util.List;

@Component
public class AddToRepairListAction implements Action {

    private final ClientService clientService;
    private final TelegramSender telegramSender;
    private final PeripheryService peripheryService;
    private String serialNumber;

    private final List<Long> managersList = Arrays.asList(995895596L, 1402868644L, 180802438L);

    @Autowired
    public AddToRepairListAction(ClientService clientService, TelegramSender telegramSender, PeripheryService peripheryService) {
        this.clientService = clientService;
        this.telegramSender = telegramSender;
        this.peripheryService = peripheryService;
    }

    @Override
    public void execute(Long chatId, String text) {
        addToRepairList(chatId, text);
    }

    private void addToRepairList(Long chatId, String text) {
        Client client = clientService.findOne(chatId);
        String action = client.getAction();

        if ("add_to_repair_list".equals(action)) {
            saveSerialNumber(chatId, text);
        }
        if ("add_description".equals(action)) {
            savePeriphery(chatId, text);
        }
    }

    private void saveSerialNumber(Long chatId, String serialNumber) {
        Periphery periphery = peripheryService.findBySerialNumber(serialNumber);
        if (periphery == null) {
            sendAddToRepairListMessage(chatId, "Сначала добавьте периферию в список (/add_periphery)");
        } else {
            this.serialNumber = serialNumber;
            Client client = clientService.findOne(chatId);
            client.setAction("add_description");
            clientService.save(client);
            sendAddToRepairListMessage(chatId, "Введите примечание");
        }
    }

    private void savePeriphery(Long chatId, String description) {
        Periphery periphery = peripheryService.findBySerialNumber(serialNumber);
        periphery.setIsRepairing(true);
        periphery.setDescription(description);
        peripheryService.save(periphery);
        this.serialNumber = null;
        setAction(chatId);
        sendAddToRepairListMessage(chatId, "Периферия добавлена в ремонт");
        sendMessageToManagers(periphery);
    }

    private void sendMessageToManagers(Periphery periphery) {
        String message = "Появилась новая периферия в ремонт.\n" +
                periphery.getName() + "\n" +
                "S/N: " + periphery.getSerialNumber() + "\n" +
                "Примечание: " + periphery.getDescription();

        for (Long manager : managersList) {
            telegramSender.sendMessage(manager, message);
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendAddToRepairListMessage(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }
}
