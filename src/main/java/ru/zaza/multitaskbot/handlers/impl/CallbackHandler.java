package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.commands.impl.DeleteTaskCommand;
import ru.zaza.multitaskbot.commands.impl.DeleteTasksCommand;
import ru.zaza.multitaskbot.handlers.Handler;

@Component
public class CallbackHandler implements Handler {

    private final DeleteTaskCommand deleteTaskCommand;
    private final DeleteTasksCommand deleteTasksCommand;

    @Autowired
    public CallbackHandler(DeleteTaskCommand deleteTaskCommand, DeleteTasksCommand deleteTasksCommand) {
        this.deleteTaskCommand = deleteTaskCommand;
        this.deleteTasksCommand = deleteTasksCommand;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        handleInline(callbackQuery);
    }

    private void handleInline(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getFrom().getId();
        String data = callbackQuery.getData();

        if (data.equals("delete_task")) {
            deleteTaskCommand.execute(chatId);
        }
        if (data.equals("delete_tasks")) {
            deleteTasksCommand.execute(chatId);
        }
    }
}
