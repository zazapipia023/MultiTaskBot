package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.actions.impl.*;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.handlers.Handler;
import ru.zaza.multitaskbot.services.ClientService;

@Component
public class ActionHandler implements Handler {

    private final ClientService clientService;
    private final AddPeripheryAction addPeriphery;
    private final DeletePeripheryAction deletePeriphery;
    private final AddToRepairListAction addRepairList;
    private final DeleteFromRepairListAction deleteRepairList;
    private final AddTaskAction addTaskAction;
    private final DeleteTaskAction deleteTaskAction;

    @Autowired
    public ActionHandler(ClientService clientService, AddPeripheryAction addPeriphery,
                         DeletePeripheryAction deletePeriphery, AddToRepairListAction addRepairList,
                         DeleteFromRepairListAction deleteRepairList, AddTaskAction addTaskAction,
                         DeleteTaskAction deleteTaskAction) {
        this.clientService = clientService;
        this.addPeriphery = addPeriphery;
        this.deletePeriphery = deletePeriphery;
        this.addRepairList = addRepairList;
        this.deleteRepairList = deleteRepairList;
        this.addTaskAction = addTaskAction;
        this.deleteTaskAction = deleteTaskAction;
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
            addPeriphery.execute(chatId, text);
        }
        if ("delete_periphery".equals(action)) {
            deletePeriphery.execute(chatId, text);
        }
        if ("add_to_repair_list".equals(action)) {
            addRepairList.execute(chatId, text);
        }
        if ("delete_from_repair_list".equals(action)) {
            deleteRepairList.execute(chatId, text);
        }
        if ("add_task".equals(action)) {
            addTaskAction.execute(chatId, text);
        }
        if ("delete_task".equals(action)) {
            deleteTaskAction.execute(chatId, text);
        }
    }
}