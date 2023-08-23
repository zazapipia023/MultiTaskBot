package ru.zaza.multitaskbot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MakeReportCommand implements Command<Long> {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendMakeReportMessage(chatId);
    }

    private void sendMakeReportMessage(Long chatId) {
        String message = "Напишите отчет";
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("make_report");
        clientService.save(client);
    }
}
