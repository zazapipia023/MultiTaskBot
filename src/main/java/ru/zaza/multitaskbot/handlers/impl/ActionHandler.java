package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.actions.impl.AddPeripheryAction;
import ru.zaza.multitaskbot.entities.User;
import ru.zaza.multitaskbot.handlers.Handler;
import ru.zaza.multitaskbot.services.UserService;

@Component
public class ActionHandler implements Handler {

    private final UserService userService;
    private final AddPeripheryAction addPeripheryAction;

    @Autowired
    public ActionHandler(UserService userService, AddPeripheryAction addPeripheryAction) {
        this.userService = userService;
        this.addPeripheryAction = addPeripheryAction;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasMessage() && update.getMessage().getReplyMarkup() == null;
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        User user = userService.findOne(chatId);
        String action = user != null ? user.getAction() : null;


        if ("add_periphery".equals(action)) {
            addPeripheryAction.execute(chatId, text);
            user.setAction("none"); // TODO: if serial_num is not correct, should be add_periphery action
            userService.save(user);
        }
        if ("add_periphery_name".equals(action)) {
            addPeripheryAction.execute(chatId, text);
            user.setAction("add_periphery");
            userService.save(user);
        } else if ("delete_periphery".equals(action)) {
            // TODO: deletePeripheryAction.execute(chatId, text);
        }
    }
}