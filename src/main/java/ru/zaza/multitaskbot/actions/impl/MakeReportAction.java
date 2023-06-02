package ru.zaza.multitaskbot.actions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;

import java.util.Arrays;
import java.util.List;

@Component
public class MakeReportAction implements Action {

    private final ClientService clientService;
    private final TelegramSender telegramSender;

    private final List<Long> managersList = Arrays.asList(995895596L, 974626403L, 1402868644L);

    @Autowired
    public MakeReportAction(ClientService clientService, TelegramSender telegramSender) {
        this.clientService = clientService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId, String text) {
        sendReportToManagers(text);
        setAction(chatId);
        sendMakeReportMessage(chatId);
    }

    private void sendMakeReportMessage(Long chatId) {
        String message = "Отчет отправлен";
        telegramSender.sendMessage(chatId, message);
    }

    private void sendReportToManagers(String report) {
        report = "Отчёт за смену:\n\n" + report;

        for (Long managerId :
             managersList) {
            telegramSender.sendMessage(managerId, report);
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }
}
