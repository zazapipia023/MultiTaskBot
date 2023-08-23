package ru.zaza.multitaskbot.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.actions.impl.*;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.handlers.Handler;
import ru.zaza.multitaskbot.services.ClientService;

@Component
@RequiredArgsConstructor
public class ActionHandler implements Handler {

    private final ClientService clientService;
    private final AddPeripheryAction addPeriphery;
    private final DeletePeripheryAction deletePeriphery;
    private final AddToRepairListAction addRepairList;
    private final DeleteFromRepairListAction deleteRepairList;
    private final AddTaskAction addTaskAction;
    private final DeleteTaskAction deleteTaskAction;
    private final MakeReportAction makeReportAction;

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

        if ("make_report".equals(action)) {
            makeReportAction.execute(chatId, text);
        }
        if ("add_task".equals(action)) {
            addTaskAction.execute(chatId, text);
        }
        if ("delete_task".equals(action)) {
            deleteTaskAction.execute(chatId, text);
        }
        if ("add_periphery".equals(action) || "add_periphery_name".equals(action)) {
            addPeriphery.execute(chatId, text);
        }
        if ("delete_periphery".equals(action)) {
            deletePeriphery.execute(chatId, text);
        }
        if ("add_to_repair_list".equals(action)) {
            addRepairList.execute(chatId, text);
        }
        if ("add_description".equals(action)) {
            addRepairList.execute(chatId, text);
        }
        if ("delete_from_repair_list".equals(action)) {
            deleteRepairList.execute(chatId, text);
        }
    }
}