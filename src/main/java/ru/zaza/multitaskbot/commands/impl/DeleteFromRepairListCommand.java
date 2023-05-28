package ru.zaza.multitaskbot.commands.impl;

import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.senders.TelegramSender;

@Component
public class DeleteFromRepairListCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    public DeleteFromRepairListCommand(ClientService clientService, TelegramSender telegramSender) {
        this.clientService = clientService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendDeleteFromRepairListMessage(chatId);
    }

    private void sendDeleteFromRepairListMessage(Long chatId) {
        String message = "Введите серийный номер";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("delete_from_repair_list");
        clientService.save(client);
    }
}
