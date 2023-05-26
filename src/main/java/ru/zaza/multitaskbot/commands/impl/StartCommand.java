package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.services.TelegramService;
import ru.zaza.multitaskbot.services.ClientService;

@Component
public class StartCommand implements Command<Long> {

    private final TelegramService telegramService;
    private final ClientService clientService;

    @Autowired
    public StartCommand(TelegramService telegramService, ClientService clientService) {
        this.telegramService = telegramService;
        this.clientService = clientService;
    }

    @Override
    public void execute(Long chatId) {
        Client client = clientService.findOne(chatId);
        if (client == null) saveUser(chatId);

        sendStartMessage(chatId);
    }

    private void sendStartMessage(Long chatId) {
        String message = "/add_periphery - добавить перефирию на склад\n" +
                "/remove_periphery - убрать периферию со склада\n";
        telegramService.sendMessage(chatId, message);
    }

    private void saveUser(Long chatId) {
        Client client = new Client();
        client.setId(chatId);
        client.setAction("none");
        clientService.save(client);
    }
}
