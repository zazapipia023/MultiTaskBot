package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.replymarkups.ReplyMarkupsBuilder;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;

@Component
public class AddPeripheryCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Autowired
    public AddPeripheryCommand(ClientService clientService, TelegramSender telegramSender) {
        this.clientService = clientService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendAddPeripheryMessage(chatId);
    }

    private void sendAddPeripheryMessage(Long chatId) {
        String message = "Какую периферию вы хотите добавить?";
        telegramSender.sendMessage(chatId, message, ReplyMarkupsBuilder.createPeripheryListKeyboard());
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("add_periphery_name");
        clientService.save(client);
    }
}
