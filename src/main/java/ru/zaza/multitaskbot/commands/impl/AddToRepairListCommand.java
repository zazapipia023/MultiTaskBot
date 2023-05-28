package ru.zaza.multitaskbot.commands.impl;

import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.services.TelegramService;

@Component
public class AddToRepairListCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramService telegramService;

    public AddToRepairListCommand(ClientService clientService, TelegramService telegramService) {
        this.clientService = clientService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendAddToRepairListMessage(chatId);
    }

    private void sendAddToRepairListMessage(Long chatId) {
        String message = "Введите серийный номер периферии";
        telegramService.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("add_to_repair_list");
        clientService.save(client);
    }
}
