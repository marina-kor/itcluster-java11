package org.itcluster11.commands;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.services.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class StartCommand extends ServiceCommand {

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {

    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = UserService.getUserName(user);
        log.info("User {}. Command execution started {}", userName, this.getCommandIdentifier());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Привіт! Обери функцію, яку будемо виконувати");
        log.info("User {}. Command execution completed {}", userName, this.getCommandIdentifier());
    }

    private void keyBoardBuild(ReplyKeyboardMarkup replyKeyboardMarkup) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        var firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("\uD83C\uDFE1 Наші пропозиції");
        firstKeyboardRow.add("\uD83D\uDCDA Допомога");
        var secondKeyboardRow = new KeyboardRow();
        secondKeyboardRow.add("\uD83C\uDFD5 Створити власний тур");
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}