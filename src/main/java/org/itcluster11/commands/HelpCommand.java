package org.itcluster11.commands;

import org.itcluster11.services.UserService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.logging.Logger;

public class HelpCommand extends ServiceCommand {
    private final Logger logger = Logger.getLogger(HelpCommand.class.getSimpleName());

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = UserService.getUserName(user);
        logger.info(String.format("User %s. Command execution started %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Цей бот створений для...");
        logger.info(String.format("User %s. Command execution completed %s", userName,
                this.getCommandIdentifier()));
    }
}