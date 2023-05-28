package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.senders.TelegramSender;

@Component
public class DeletePeripheryCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Autowired
    public DeletePeripheryCommand(ClientService clientService, TelegramSender telegramSender) {
        this.clientService = clientService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendDeletePeripheryMessage(chatId);
    }

    public void sendDeletePeripheryMessage(Long chatId) {
        String message = "Введите серийный номер периферии, которую нужно удалить";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("delete_periphery");
        clientService.save(client);
    }
}
