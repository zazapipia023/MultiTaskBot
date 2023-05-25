package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.User;
import ru.zaza.multitaskbot.services.TelegramService;
import ru.zaza.multitaskbot.services.UserService;

@Component
public class StartCommand implements Command<Long> {

    private final TelegramService telegramService;
    private final UserService userService;

    @Autowired
    public StartCommand(TelegramService telegramService, UserService userService) {
        this.telegramService = telegramService;
        this.userService = userService;
    }

    @Override
    public void execute(Long chatId) {
        User user = userService.findOne(chatId);
        if (user == null) saveUser(chatId);

        sendStartMessage(chatId);
    }

    private void sendStartMessage(Long chatId) {
        String message = "/add_periphery - добавить перефирию на склад\n" +
                "/remove_periphery - убрать периферию со склада\n";
        telegramService.sendMessage(chatId, message);
    }

    private void saveUser(Long chatId) {
        User user = new User();
        user.setId(chatId);
        user.setAction("none");
        userService.save(user);
    }
}
