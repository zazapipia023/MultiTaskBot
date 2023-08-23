package ru.zaza.multitaskbot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;

@Component
@RequiredArgsConstructor
public class AddTaskCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendAddTaskMessage(chatId);
    }

    private void sendAddTaskMessage(Long chatId) {
        String message = "Какое задание вы хотите добавить?";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("add_task");
        clientService.save(client);
    }
}
