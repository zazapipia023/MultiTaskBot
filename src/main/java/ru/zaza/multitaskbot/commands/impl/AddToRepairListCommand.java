package ru.zaza.multitaskbot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.senders.TelegramSender;

@Component
@RequiredArgsConstructor
public class AddToRepairListCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendAddToRepairListMessage(chatId);
    }

    private void sendAddToRepairListMessage(Long chatId) {
        String message = "Введите серийный номер периферии";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("add_to_repair_list");
        clientService.save(client);
    }
}
