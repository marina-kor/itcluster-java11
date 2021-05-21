package org.itcluster11;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.bots.MailieBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class Launcher {
    public static void main(String[] args) {
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MailieBot());
        } catch (TelegramApiException exc) {
            log.debug("Telegram Bot not started by reason: ", exc);
        }
    }
}
