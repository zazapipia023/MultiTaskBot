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
public class AddTaskAction implements Action {

    private final TaskService taskService;
    private final ClientService clientService;

    private final TelegramSender telegramSender;

    @Override
    public void execute(Long chatId, String text) {
        saveGivenTask(text);
        setAction(chatId);
        sendAddTaskMessage(chatId);
        sendAddTaskMessageToReceiver();
    }

    private void saveGivenTask(String taskText) {
        Task task = new Task();
        task.setTask(taskText);
        taskService.save(task);
    }

    private void sendAddTaskMessage(Long chatId) {
        telegramSender.sendMessage(chatId, "Задание добавлено");
    }

    private void sendAddTaskMessageToReceiver() {
        String message = "Добавлено новое задание!\n" +
                "Напишите /get_tasks для просмотра";
        telegramSender.sendMessageToTaskReceiver(message);
    }

    private void setAction(Long chatId) {
        Client client = clientService.findOne(chatId);
        client.setAction("none");
        clientService.save(client);
    }
}
