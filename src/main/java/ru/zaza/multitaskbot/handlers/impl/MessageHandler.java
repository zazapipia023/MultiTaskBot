package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.commands.Commands;
import ru.zaza.multitaskbot.commands.impl.StartCommand;
import ru.zaza.multitaskbot.handlers.Handler;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandler implements Handler {

    private final StartCommand startCommand;

    private final Map<String, Command<Long>> commands = createCommandHandlers();

    private Map<String, Command<Long>> createCommandHandlers() {
        Map<String, Command<Long>> result = new HashMap<>();

        return result;
    }

    @Autowired
    public MessageHandler(StartCommand startCommand) {
        this.startCommand = startCommand;
    }

    @Override
    public boolean supports(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }

        String text = update.getMessage().getText();
        return text.startsWith(Commands.START_COMMAND) || commands.containsKey(text);
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.startsWith(Commands.START_COMMAND)) {
            startCommand.execute(chatId);
        } else if (commands.containsKey(text)) {
            commands.get(text).execute(chatId);
        }
    }
}
