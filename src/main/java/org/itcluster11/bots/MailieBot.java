package org.itcluster11.bots;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.commands.HelpCommand;
import org.itcluster11.commands.NewTourCommand;
import org.itcluster11.commands.StartCommand;
import org.itcluster11.model.Category;
import org.itcluster11.model.Point;
import org.itcluster11.model.SearchConfiguration;
import org.itcluster11.repository.CategoryRepository;
import org.itcluster11.repository.PointRepository;
import org.itcluster11.repository.SearchConfigurationRepository;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MailieBot extends TelegramLongPollingCommandBot {

    SearchConfigurationRepository searchConfigurationRepository = new SearchConfigurationRepository();
    CategoryRepository categoryRepository = new CategoryRepository();
    PointRepository pointRepository = new PointRepository();

    public MailieBot() {
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        register(new StartCommand("start", "getting started with the bot"));
        register(new HelpCommand("help", "shows all commands. Use /help [command] for more info"));
        register(new NewTourCommand("tour", "shows all categories. Make your choice."));

    }

    @Override
    public void processNonCommandUpdate(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String text = update.getMessage().getText();
//            log.info("Received message: {}", text);
//            var chatId = update.getMessage().getChatId().toString();
//            sendAnswer(chatId, text);
//            sendAnswer(chatId, text);
//        }


        String categoryName = update.getMessage().getText();
        Long userId = update.getMessage().getFrom().getId();

        SearchConfiguration config = searchConfigurationRepository.findByUserId(userId);

        if (config == null) {
            config = SearchConfiguration
                    .builder()
                    .userId(userId)
                    .categories(new ArrayList<>())
                    .build();
            searchConfigurationRepository.save(config);
        }

        Category category = categoryRepository.findByName(categoryName);
        var chatId = update.getMessage().getChatId().toString();
        if (category == null) {
            sendAnswer(chatId, "Невідома категорія");
            return;
        }

        if (config.getCategories().contains(category)) {
            searchConfigurationRepository.unLinkSearchConfigurationToCategory(config.getId(), category.getId());
        } else {
            searchConfigurationRepository.linkSearchConfigurationToCategory(config.getId(), category.getId());
        }

        config = searchConfigurationRepository.findById(config.getId());
        List<Point> points = pointRepository.findPointsByCategories(config.getCategories());

        sendAnswer(chatId, "Зараз вибрані категорії: \n"
                + formatCategoryList(config.getCategories())
                + " Знайдені локації: \n"
                + formatPointList(points)
        );
    }

    private String formatCategoryList(List<Category> categories) {
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(category.getName());
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatPointList(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append(point.getName());
            sb.append("\n");
        }

        return sb.toString();
    }


    @Override
    public void processInvalidCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            log.info("Received message: {}", text);
            var chatId = update.getMessage().getChatId().toString();
            sendAnswer(chatId, "Вибач, я не можу на це відповісти \uD83D\uDE05");
        }
    }

    private void sendAnswer(String chatId, String text) {
        var message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
            log.info("Message sent: {}", message.getText());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "MailieBot";
    }

    @Override
    public String getBotToken() {
        return "1729304112:AAHEM1sI3qbXqtpHA920RwbvZ312znp-3ls";
    }
}
