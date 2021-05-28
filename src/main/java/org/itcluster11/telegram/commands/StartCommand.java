package org.itcluster11.telegram.commands;

import lombok.extern.slf4j.Slf4j;

import org.itcluster11.telegram.services.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.itcluster11.telegram.bots.MailieBot.REPLY_KEYBOARD_MARKUP;

@Slf4j
public class StartCommand extends ServiceBotCommand {

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Update update) {
        String userName = UserService.getUserName(update.getMessage());
        var message = new SendMessage();
        Long chatId = update.getMessage().getChatId();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText("Привіт! Обери функцію, яку будемо виконувати");
        message.setReplyMarkup(REPLY_KEYBOARD_MARKUP);
        keyBoardBuild();
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Warning %s. Command %s. User: %s", e.getMessage(), getCommandIdentifier(), userName), e);
        }
    }

    @Override
    public void execute(AbsSender absSender, Update update,String... params) {
        String userName = UserService.getUserName(update.getMessage());
        log.info("User {}. Command execution started {}", userName, this.getCommandIdentifier());
        sendAnswer(absSender, update);
        log.info("User {}. Command execution completed {}", userName, this.getCommandIdentifier());
    }

    private void keyBoardBuild() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        var firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add("\uD83C\uDFE1 Наші пропозиції");
        firstKeyboardRow.add("\uD83D\uDCDA Допомога");
        var secondKeyboardRow = new KeyboardRow();
        secondKeyboardRow.add("\uD83C\uDFD5 Створити власний тур");
        keyboard.add(firstKeyboardRow);
        keyboard.add(secondKeyboardRow);
        REPLY_KEYBOARD_MARKUP.setKeyboard(keyboard);
    }
}