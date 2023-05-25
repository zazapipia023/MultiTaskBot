package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.User;
import ru.zaza.multitaskbot.replymarkups.ReplyMarkupsBuilder;
import ru.zaza.multitaskbot.services.TelegramService;
import ru.zaza.multitaskbot.services.UserService;

@Component
public class AddPeripheryCommand implements Command<Long> {

    private final UserService userService;
    private final TelegramService telegramService;

    @Autowired
    public AddPeripheryCommand(UserService userService, TelegramService telegramService) {
        this.userService = userService;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(Long chatId) {
        setAction(chatId);
        sendAddPeripheryMessage(chatId);
    }

    private void setAction(Long chatId) {
        User user = userService.findOne(chatId);
        user.setAction("add_periphery_name");
        userService.save(user);
    }

    private void sendAddPeripheryMessage(Long chatId) {
        String message = "Какую периферию вы хотите добавить?";
        telegramService.sendMessage(chatId, message, ReplyMarkupsBuilder.createPeripheryListKeyboard());
    }
}
