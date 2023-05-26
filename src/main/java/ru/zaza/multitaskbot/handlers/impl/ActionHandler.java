package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.actions.impl.AddPeripheryAction;
import ru.zaza.multitaskbot.actions.impl.DeletePeripheryAction;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.handlers.Handler;
import ru.zaza.multitaskbot.services.ClientService;

@Component
public class ActionHandler implements Handler {

    private final ClientService clientService;
    private final AddPeripheryAction addPeripheryAction;
    private final DeletePeripheryAction deletePeripheryAction;

    @Autowired
    public ActionHandler(ClientService clientService, AddPeripheryAction addPeripheryAction, DeletePeripheryAction deletePeripheryAction) {
        this.clientService = clientService;
        this.addPeripheryAction = addPeripheryAction;
        this.deletePeripheryAction = deletePeripheryAction;
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

        Client client = clientService.findOne(chatId);
        String action = client != null ? client.getAction() : null;


        if ("add_periphery".equals(action) || "add_periphery_name".equals(action)) {
            addPeripheryAction.execute(chatId, text);
        }
        if ("delete_periphery".equals(action)) {
            deletePeripheryAction.execute(chatId, text);
        }
    }
}