package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.commands.Command;
import ru.zaza.multitaskbot.commands.Commands;
import ru.zaza.multitaskbot.commands.impl.*;
import ru.zaza.multitaskbot.handlers.Handler;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandler implements Handler {

    private final StartCommand startCommand;
    private final AddPeripheryCommand addPeripheryCommand;
    private final DeletePeripheryCommand deletePeripheryCommand;
    private final GetPeripheryCommand getPeripheryCommand;
    private final GetRepPeripheryCommand getRepPeripheryCommand;

    private Map<String, Command<Long>> commands;

    private void createCommandHandlers() {
        commands = new HashMap<>();

        commands.put(Commands.ADD_PERIPHERY_COMMAND, addPeripheryCommand);
        commands.put(Commands.DELETE_PERIPHERY_COMMAND, deletePeripheryCommand);
        commands.put(Commands.GET_PERIPHERY_COMMAND, getPeripheryCommand);
        commands.put(Commands.GET_REP_PERIPHERY_COMMAND, getRepPeripheryCommand);
    }

    @Autowired
    public MessageHandler(StartCommand startCommand, AddPeripheryCommand addPeripheryCommand, DeletePeripheryCommand deletePeripheryCommand, GetPeripheryCommand getPeripheryCommand, GetRepPeripheryCommand getRepPeripheryCommand) {
        this.startCommand = startCommand;
        this.addPeripheryCommand = addPeripheryCommand;
        this.deletePeripheryCommand = deletePeripheryCommand;
        this.getPeripheryCommand = getPeripheryCommand;
        this.getRepPeripheryCommand = getRepPeripheryCommand;
        createCommandHandlers();
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
