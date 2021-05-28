
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
public class HelpCommand extends ServiceBotCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Update update) {
        String userName = UserService.getUserName(update.getMessage());
        Long chatId = update.getMessage().getChatId();
        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText("Цей бот створений для пошуку Вашого маршруту :)");
        message.setReplyMarkup(REPLY_KEYBOARD_MARKUP);
        keyBoardBuild();
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Warning %s. Command %s. User: %s", e.getMessage(), getCommandIdentifier(), userName), e);
            e.printStackTrace();
        }
    }

    @Override
    public void execute(AbsSender absSender, Update update, String... params) {
        String userName = UserService.getUserName(update.getMessage());
        log.info("User {}. Command execution started {}", userName,
                this.getCommandIdentifier());
        sendAnswer(absSender, update);
        log.info("User {}. Command execution completed {}", userName,
                this.getCommandIdentifier());
    }

    private void keyBoardBuild() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        //TODO
        REPLY_KEYBOARD_MARKUP.setKeyboard(keyboard);
    }
}