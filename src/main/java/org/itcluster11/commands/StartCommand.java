package org.itcluster11.commands;

import org.itcluster11.services.UserService;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.logging.Level;
import java.util.logging.Logger;


public class StartCommand extends ServiceCommand {
    private Logger logger = Logger.getLogger(StartCommand.class.getSimpleName());

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = UserService.getUserName(user);
        logger.info(String.format("User %s. Command execution started %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Привіт, натисни /help, щоб отримати більше інформації");
        logger.info(String.format("User %s. Command execution completed %s", userName,
                this.getCommandIdentifier()));
    }
}