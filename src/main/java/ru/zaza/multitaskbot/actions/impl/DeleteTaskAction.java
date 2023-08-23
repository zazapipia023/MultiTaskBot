package ru.zaza.multitaskbot.actions.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.actions.Action;
import ru.zaza.multitaskbot.entities.Client;
import ru.zaza.multitaskbot.entities.Task;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.ClientService;
import ru.zaza.multitaskbot.services.TaskService;

@Component
@RequiredArgsConstructor
public class DeleteTaskAction implements Action {

    private final TaskService taskService;
    private final ClientService clientService;
    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId, String text) {
        if (deleteTask(text)) {
            setAction(chatId);
            sendDeleteTaskAction(chatId, "Задание удалено");
        } else {
            sendDeleteTaskAction(chatId, "Задания с таким ID не существует");
        }
    }

    private boolean deleteTask(String taskId) {
        Task task = taskService.findOne(Integer.parseInt(taskId));
        if (task != null) {
            taskService.delete(task);
            return true;
        } else {
            return false;
        }
    }

    private void sendDeleteTaskAction(Long chatId, String message) {
        telegramSender.sendMessage(chatId, message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }
}
