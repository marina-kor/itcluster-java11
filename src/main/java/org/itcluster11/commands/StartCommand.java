package org.itcluster11.commands;

import org.itcluster11.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class StartCommand extends ServiceCommand {
    private final Logger logger = LoggerFactory.getLogger(StartCommand.class.getSimpleName());
    private final ReplyKeyboardMarkup replyKeyboardMarkup;

    public StartCommand(String identifier, String description, ReplyKeyboardMarkup replyKeyboardMarkup) {
        super(identifier, description);
        this.replyKeyboardMarkup = replyKeyboardMarkup;
    }

    @Override
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(replyKeyboardMarkup);
        keyBoardBuild(replyKeyboardMarkup);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(String.format("Warning %s. Command %s. User: %s", e.getMessage(), commandName, userName), e);
            e.printStackTrace();
        }
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = UserService.getUserName(user);
        logger.info(String.format("User %s. Command execution started %s", userName,
                this.getCommandIdentifier()));
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Привіт! Обери функцію, яку будемо виконувати");
        logger.info(String.format("User %s. Command execution completed %s", userName,
                this.getCommandIdentifier()));
    }

    private void keyBoardBuild(ReplyKeyboardMarkup replyKeyboardMarkup) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("\uD83C\uDFE1 Наші пропозиції");
        firstKeyboardRow.add("\uD83D\uDCDA Допомога");
        KeyboardRow secondKeyboardRow = new KeyboardRow();
        secondKeyboardRow.add("\uD83C\uDFD5 Створити власний тур");
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}