package org.itcluster11.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class ServiceCommand extends BotCommand {
    public ServiceCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    abstract void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text);
}