package org.itcluster11.telegram.commands;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class ServiceBotCommand {
    @Getter
    @Setter
    private String commandIdentifier;
    @Getter
    @Setter
    private String description;


    protected ServiceBotCommand(String commandIdentifier, String description) {
        this.commandIdentifier = commandIdentifier;
        this.description = description;
    }

    public abstract void execute(AbsSender absSender, Update update, String... params);
}
