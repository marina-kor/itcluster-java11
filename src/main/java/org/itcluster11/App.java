package org.itcluster11;

import org.itcluster11.model.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("MailieBot", "1729304112:AAHEM1sI3qbXqtpHA920RwbvZ312znp-3ls"));
        } catch (TelegramApiException exc) {
            exc.printStackTrace();
        }
    }
}
