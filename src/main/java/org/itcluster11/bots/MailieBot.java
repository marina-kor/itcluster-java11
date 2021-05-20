package org.itcluster11.bots;

import org.itcluster11.commands.HelpCommand;
import org.itcluster11.commands.StartCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MailieBot extends TelegramLongPollingCommandBot {
    private static MailieBot MAILIE_BOT;

    private MailieBot() {
        register(new HelpCommand("help", "shows all commands. Use /help [command] for more info"));
        register(new StartCommand("start", "getting started with the bot"));
    }

    public static MailieBot getInstance() {
        synchronized (MailieBot.class) {
            if (MAILIE_BOT == null) {
                MAILIE_BOT = new MailieBot();
            }
            return MAILIE_BOT;
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            System.out.println("Received message: " + text);
            String chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, text);
        }
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            System.out.println("Received message: " + text);
            String chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, "Вибач, я не можу на це відповісти \uD83D\uDE05");
        }
    }

    private void sendAnswer(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            System.out.println("Message sent: " + message.getText());
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "MailieBot";
    }

    @Override
    public String getBotToken() {
        String BOT_TOKEN = "1729304112:AAHEM1sI3qbXqtpHA920RwbvZ312znp-3ls";
        return BOT_TOKEN;
    }
}
