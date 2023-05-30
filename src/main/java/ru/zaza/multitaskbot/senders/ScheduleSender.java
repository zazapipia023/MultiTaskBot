package ru.zaza.multitaskbot.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zaza.multitaskbot.config.BotConfig;

@Component
public class ScheduleSender extends DefaultAbsSender {

    private final BotConfig botConfig;

    @Value("${task.receiver}")
    private Long taskReceiver;

    @Autowired
    public ScheduleSender(BotConfig botConfig) {
        super(new DefaultBotOptions());
        this.botConfig = botConfig;
    }

    @Scheduled(cron = "0 0 8 * * *")
    private void sendFillFridgeMessage() {
        sendMessage(taskReceiver, "Нужно заполнить холодильник, и прислать фотографию в рабочий чат");
    }

    @Scheduled(cron = "0 30 9,21 * * *")
    private void sendMakeReportMessage() {
        sendMessage(taskReceiver, "Напишите отчет за смену");
    }

    @Scheduled(cron = "0 15 10 * * *")
    private void sendCleaningMessage() {
        sendMessage(taskReceiver, "Нужно провести полную уборку зала");
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }
}
