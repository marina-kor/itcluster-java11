package org.itcluster11.bots;

import org.itcluster11.commands.HelpCommand;
import org.itcluster11.commands.StartCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MailieBot extends TelegramLongPollingCommandBot {
    private final Logger logger = LoggerFactory.getLogger(MailieBot.class);

    public MailieBot() {
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        register(new StartCommand("start", "getting started with the bot", replyKeyboardMarkup));
        register(new HelpCommand("help", "shows all commands. Use /help [command] for more info"));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            logger.info("Received message: {}", text);
            var chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, text);
        }
    }

    @Override
    public void processInvalidCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            logger.info("Received message: {}", text);
            var chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, "Вибач, я не можу на це відповісти \uD83D\uDE05");
        }
    }

    private void sendAnswer(String chatId, String text) {
        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            logger.info("Message sent: {}", message.getText());
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "MailieBot";
    }

    @Override
    public String getBotToken() {
        return "1729304112:AAHEM1sI3qbXqtpHA920RwbvZ312znp-3ls";
    }
}
