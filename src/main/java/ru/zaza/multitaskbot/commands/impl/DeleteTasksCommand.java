package ru.zaza.multitaskbot.commands.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.senders.TelegramSender;
import ru.zaza.multitaskbot.services.TaskService;

@Component
@RequiredArgsConstructor
public class DeleteTasksCommand implements Command<Long> {

    private final TaskService taskService;
    private final TelegramSender telegramSender;

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
