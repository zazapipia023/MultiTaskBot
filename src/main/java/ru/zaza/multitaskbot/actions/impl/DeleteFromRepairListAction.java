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
public class DeleteFromRepairListAction implements Action {

    private final ClientService clientService;
    private final PeripheryService peripheryService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId, String text) {
        removeFromRepairList(chatId, text);
    }

    private void removeFromRepairList(Long chatId, String text) {
        Periphery periphery = peripheryService.findBySerialNumber(text);
        if (periphery == null || !periphery.getIsRepairing()) {
            sendDeleteFromRepairListMessage(chatId, "Нет периферии с таким серийным номером в ремонте");
        }
        else {
            periphery.setIsRepairing(false);
            periphery.setDescription("С ремонта");
            peripheryService.save(periphery);
            setAction(chatId);
            sendDeleteFromRepairListMessage(chatId, "Периферия убрана из ремонта");
        }
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendDeleteFromRepairListMessage(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }
}
