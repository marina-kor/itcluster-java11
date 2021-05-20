package org.itcluster11.commands;


import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;
import java.util.logging.Logger;


abstract public class ServiceCommand extends BotCommand {
    private final Logger logger = Logger.getLogger(ServiceCommand.class.getSimpleName());

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.log(Level.WARNING, String.format("Warning %s. Command %s. User: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}