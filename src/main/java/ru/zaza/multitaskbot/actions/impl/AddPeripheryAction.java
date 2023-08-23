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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AddPeripheryAction implements Action {

    private final PeripheryService peripheryService;
    private final ClientService clientService;
    private final TelegramSender telegramSender;

    private String peripheryName;

    private static final Pattern SERIAL_NUMBER_PATTERN = Pattern.compile("\\A[a-zA-Z0-9]{12}\\Z");

    @Override
    public void execute(Long chatId, String text) {
        Client client = clientService.findOne(chatId);
        String action = client.getAction();

        if ("add_periphery_name".equals(action)) savePeripheryName(chatId, text);
        if ("add_periphery".equals(action)) saveSerialNumber(chatId, text);
    }

    private void saveSerialNumber(Long chatId, String serialNumber) {
        Matcher matcher = SERIAL_NUMBER_PATTERN.matcher(serialNumber);
        if (!matcher.find()) {
            sendAddPeripheryMessage(chatId, "Введен неверный серийный номер");
            return;
        }
        if (peripheryService.findBySerialNumber(serialNumber) != null) {
            sendAddPeripheryMessage(chatId, "Периферия с таким серийным номером уже есть на складе");
            return;
        }

        Periphery periphery = new Periphery();
        periphery.setName(peripheryName);
        periphery.setSerialNumber(serialNumber);
        periphery.setIsRepairing(false);
        periphery.setDescription("Отсутствует");
        peripheryService.save(periphery);
        peripheryName = null;

        setAction(chatId);

        sendAddPeripheryMessage(chatId, "Периферия добавлена");
    }

    private void savePeripheryName(Long chatId, String name) {
        peripheryName = name;
        sendAddPeripheryMessage(chatId, "Введите серийный номер");
        Client client = clientService.findOne(chatId);
        client.setAction("add_periphery");
        clientService.save(client);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }

    private void sendAddPeripheryMessage(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }
}
