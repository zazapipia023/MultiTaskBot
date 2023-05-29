package ru.zaza.multitaskbot.commands.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.entities.Task;
import ru.zaza.multitaskbot.replymarkups.ReplyMarkupsBuilder;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.TaskService;

import java.util.List;

@Component
public class GetTasksCommand implements Command<Long> {

    private final TaskService taskService;
    private final TelegramSender telegramSender;

    @Autowired
    public GetTasksCommand(TaskService taskService, TelegramSender telegramSender) {
        this.taskService = taskService;
        this.telegramSender = telegramSender;
    }

    @Override
    public void execute(Long chatId) {
        sendGetTasksMessage(chatId);
    }

    private void sendGetTasksMessage(Long chatId) {
        telegramSender.sendMessage(chatId, writeTasks(), ReplyMarkupsBuilder.createGetTasksKeyboard());
    }

    private String writeTasks() {
        StringBuilder sb = new StringBuilder().append("Задания:\n\n");
        List<Task> list = taskService.findAll();

        for (Task task:
             list) {
            sb.append(task.getId()).append(": ").append(task.getTask()).append("\n");
        }

        return sb.toString();
    }
}
