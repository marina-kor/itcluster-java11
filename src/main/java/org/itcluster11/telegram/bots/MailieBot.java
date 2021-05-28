package org.itcluster11.telegram.bots;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.telegram.commands.*;
import org.itcluster11.telegram.services.UserService;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.HashMap;
import java.util.Objects;

@Slf4j
public class MailieBot extends TelegramLongPollingBot {

    private static final HashMap<String, ServiceBotCommand> COMMANDS = new HashMap<>();
    public static final ReplyKeyboardMarkup REPLY_KEYBOARD_MARKUP = new ReplyKeyboardMarkup();

    public MailieBot() {
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        register(new StartCommand("start", "getting started with the bot"));
        register(new HelpCommand("help", "shows all commands. Use /help [command] for more info"));
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            ServiceBotCommand command = getRegisteredCommand(text);
            if (!Objects.isNull(command)) {
                command.execute(this, update);
            } else {
                processNonCommandUpdate(update);
            }
        }
    }



    public void register(ServiceBotCommand command) {
        COMMANDS.put(command.getCommandIdentifier(), command);
    }

    public void registerAll(ServiceBotCommand... commands) {
        String commandName;
        for (var command : commands) {
            commandName = command.getCommandIdentifier();
            COMMANDS.put(commandName, command);
        }
    }

    public void deregister(String commandName) {
        COMMANDS.remove(commandName);
    }

    public ServiceBotCommand getRegisteredCommand(String name) {
        return COMMANDS.get(name);
    }

    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            String userName = UserService.getUserName(update.getMessage());
            log.info("Received message: {}", text);
            var chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, text, userName);
        }
    }

    private void sendAnswer(String chatId, String text, String userName) {
        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            log.info("Message sent to {}: {}", userName, message.getText());
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
