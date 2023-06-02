package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;

@Component
public class StartCommand implements Command<Long> {

    private final TelegramSender telegramSender;
    private final ClientService clientService;

    @Autowired
    public StartCommand(TelegramSender telegramSender, ClientService clientService) {
        this.telegramSender = telegramSender;
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
                "/delete_periphery - убрать периферию со склада\n" +
                "/get_periphery - периферия на складе\n" +
                "/add_to_repair_list - добавить в ремонт(периферия сначала должна быть добавлена на склад)\n" +
                "/delete_from_repair_list - убрать из ремонта\n" +
                "/add_task - добавить задание\n" +
                "/get_tasks - посмотреть задания\n" +
                "/delete_task - удалить задание\n" +
                "/make_report - сделать отчет за смену";
        telegramSender.sendMessage(chatId, message);
    }

    private void saveUser(Long chatId) {
        Client client = new Client();
        client.setId(chatId);
        client.setAction("none");
        clientService.save(client);
    }
}
