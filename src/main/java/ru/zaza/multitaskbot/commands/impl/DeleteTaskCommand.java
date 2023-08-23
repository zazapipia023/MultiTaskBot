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
public class DeleteTaskCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendDeleteTaskMessage(chatId);
    }

    private void sendDeleteTaskMessage(Long chatId) {
        String message = "Выберите ID задания, которое нужно удалить";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("delete_task");
        clientService.save(client);
    }
}
