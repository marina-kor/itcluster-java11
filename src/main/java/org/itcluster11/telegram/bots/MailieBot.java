package org.itcluster11.telegram.bots;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.telegram.commands.HelpCommand;
import org.itcluster11.telegram.commands.NewTourCommand;
import org.itcluster11.telegram.commands.StartCommand;
import org.itcluster11.telegram.services.ViewService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class MailieBot extends TelegramLongPollingCommandBot {

    ViewService viewService = new ViewService();

    public MailieBot() {
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        register(new StartCommand("start", "getting started with the bot"));
        register(new HelpCommand("help", "shows all commands. Use /help [command] for more info"));
        register(new NewTourCommand("tour", "shows all categories. Make your choice."));

    }

    @Override
    public void processNonCommandUpdate(Update update) {

        Long userId = update.getMessage().getFrom().getId();
        var chatId = update.getMessage().getChatId().toString();
        var text = "";

        if (isLocationMessage(update)) {
            var location = update.getMessage().getLocation();
            Double lng = location.getLongitude();
            Double lat = location.getLatitude();
            text = viewService.processLocation(lng, lat, userId);
        } else if (isRadiusMessage(update)) {
            var radius = Integer.parseInt(update.getMessage().getText());
            text = viewService.processRadius(userId, radius);

        } else {
            String categoryName = update.getMessage().getText();
            text = viewService.processCategory(categoryName, userId);
        }

        sendAnswer(chatId, text);
    }

    private boolean isLocationMessage(Update update) {
        return update.getMessage().getLocation() != null;
    }

    private boolean isRadiusMessage(Update update) {
        String message = update.getMessage().getText();

        try {
            Integer.valueOf(message);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


    @Override
    public void processInvalidCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            log.info("Received message: {}", text);
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
            log.info("Message sent: {}", message.getText());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
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
