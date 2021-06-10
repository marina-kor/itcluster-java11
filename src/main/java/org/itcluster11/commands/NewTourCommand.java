package org.itcluster11.commands;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Category;
import org.itcluster11.repository.CategoryRepository;
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

public class NewTourCommand extends ServiceCommand {
    CategoryRepository categoryRepository = new CategoryRepository();

    public NewTourCommand(String identifier, String description) {
        super(identifier, description);
    }

    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(replyKeyboardMarkup);
        keyBoardBuild(replyKeyboardMarkup);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Warning %s. Command %s. User: %s", e.getMessage(), commandName, userName), e);
        }
    }

    private void keyBoardBuild(ReplyKeyboardMarkup replyKeyboardMarkup) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            var keyboardRow = new KeyboardRow();
            keyboardRow.add(category.getName());

            keyboard.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);
    }


    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = UserService.getUserName(user);
        log.info("User {}. Command execution started {}", userName, this.getCommandIdentifier());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, "Оберіть категорії");
        log.info("User {}. Command execution completed {}", userName, this.getCommandIdentifier());
    }
}

