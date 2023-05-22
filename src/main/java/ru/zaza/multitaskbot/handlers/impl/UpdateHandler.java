package ru.zaza.multitaskbot.handlers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.handlers.Handler;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class UpdateHandler implements Handler {


    private final MessageHandler messageHandler;

    private Set<Handler> getHandlers() {
        Set<Handler> result = new LinkedHashSet<>();

        result.add(messageHandler);

        return result;
    }

    @Autowired
    public UpdateHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }

    @Override
    public void handle(Update update) {
        try {
            handleUpdate(update);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleUpdate(Update update) {
        getHandlers().stream()
                .filter(handler -> handler.supports(update))
                .findFirst()
                .ifPresent(handler -> handler.handle(update));
    }
}
