package ru.zaza.multitaskbot.commands.impl;

import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.TaskService;

@Component
public class DeleteTasksCommand implements Command<Long> {

    private final TaskService taskService;
    private final TelegramSender telegramSender;

    public DeleteTasksCommand(TaskService taskService, TelegramSender telegramSender) {
        this.taskService = taskService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        delete();
        sendDeleteTasksMessage(chatId);
    }

    private void delete() {
        taskService.deleteAll();
    }

    private void sendDeleteTasksMessage(Long chatId) {
        String message = "Задания удалены";
        telegramSender.sendMessage(chatId, message);
    }
}
