package ru.zaza.multitaskbot.actions.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.senders.TelegramSender;

@Component
@RequiredArgsConstructor
public class DeletePeripheryAction implements Action {

    private final PeripheryService peripheryService;
    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId, String text) {
        deletePeriphery(chatId, text);
    }

    private void deletePeriphery(Long chatId, String serialNumber) {
        Periphery periphery = peripheryService.findBySerialNumber(serialNumber);
        if (periphery == null) sendDeletePeripheryMessage(chatId, "Серийный номер не найден");
        else {
            peripheryService.delete(periphery);
            setAction(chatId);
            sendDeletePeripheryMessage(chatId, "Периферия удалена");
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendDeletePeripheryMessage(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }
}
