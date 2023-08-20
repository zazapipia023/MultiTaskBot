package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.commands.impl.DeleteTaskCommand;
import ru.zaza.multitaskbot.commands.impl.DeleteTasksCommand;
import ru.zaza.multitaskbot.handlers.Handler;
import ru.zaza.multitaskbot.senders.TelegramSender;

@Component
public class CallbackHandler implements Handler {

    private final DeleteTaskCommand deleteTaskCommand;
    private final DeleteTasksCommand deleteTasksCommand;
    private final TelegramSender telegramSender;

    @Autowired
    public CallbackHandler(DeleteTaskCommand deleteTaskCommand, DeleteTasksCommand deleteTasksCommand, TelegramSender telegramSender) {
        this.deleteTaskCommand = deleteTaskCommand;
        this.deleteTasksCommand = deleteTasksCommand;
        this.telegramSender = telegramSender;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        handleInline(callbackQuery);
        deleteInlineKeyboard(callbackQuery);
    }

    private void deleteInlineKeyboard(CallbackQuery callbackQuery) {
        telegramSender.deleteInlineKeyboard(callbackQuery);
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
        if (data.equals("send_all_periphery")) {
            // TODO:
        }
        if (data.equals("send_periphery")) {
            // TODO:
        }
    }
}
