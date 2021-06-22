package org.itcluster11.telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class ServiceCommand extends BotCommand {
    protected ReplyKeyboardMarkup replyKeyboardMarkup;

    protected ServiceCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
    }

    abstract void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text);
}