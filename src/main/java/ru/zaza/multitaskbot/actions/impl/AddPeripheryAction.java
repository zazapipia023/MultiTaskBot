package ru.zaza.multitaskbot.actions.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Periphery;
import ru.zaza.multitaskbot.services.PeripheryService;
import ru.zaza.multitaskbot.services.TelegramService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AddPeripheryAction implements Action {

    private final PeripheryService peripheryService;
    private final TelegramService telegramService;

    private static final Pattern SERIAL_NUMBER_PATTERN = Pattern.compile("[a-zA-Z0-9]{12}");

    @Autowired
    public AddPeripheryAction(PeripheryService peripheryService, TelegramService telegramService) {
        this.peripheryService = peripheryService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId, String serial_num) {
        Matcher matcher = SERIAL_NUMBER_PATTERN.matcher(serial_num);
        if (!matcher.find()) {
            telegramService.sendMessage(chatId, "Введен неверный серийный номер");
            return;
        }

        Periphery periphery = new Periphery();
//      TODO:  periphery.setName();
        periphery.setSerialNumber(serial_num);
    }
}
