package ru.zaza.multitaskbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zaza.multitaskbot.config.BotConfig;
import ru.zaza.multitaskbot.handlers.impl.UpdateHandler;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final UpdateHandler updateHandler;

    @Autowired
    public TelegramBot(BotConfig botConfig, UpdateHandler updateHandler) {
        this.botConfig = botConfig;
        this.updateHandler = updateHandler;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (updateHandler.supports(update)) {
            updateHandler.handle(update);
        }

    }
}
