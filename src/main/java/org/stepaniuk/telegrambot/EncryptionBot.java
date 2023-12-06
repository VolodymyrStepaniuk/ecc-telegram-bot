package org.stepaniuk.telegrambot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.enums.ConversationState;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.model.UserSession;
import org.stepaniuk.telegrambot.service.UserSessionService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Log4j2
public class EncryptionBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final Dispatcher dispatcher;
    private final UserSessionService userSessionService;

    public EncryptionBot(Dispatcher dispatcher, UserSessionService userSessionService) {
        this.dispatcher = dispatcher;
        this.userSessionService = userSessionService;
    }

    /**
     * This is an entry point for any messages, or updates received from user<br>
     * Docs for "Update object: https://core.telegram.org/bots/api#update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            UserSession session = userSessionService.getSession(chatId);

            UserRequest userRequest = UserRequest
                    .builder()
                    .update(update)
                    .userSession(session)
                    .chatId(chatId)
                    .build();

            boolean dispatched = dispatcher.dispatch(userRequest);

            if (!dispatched) {
                session.setState(ConversationState.ERROR_OCCURRED);
                log.error("Error occurred while processing user request: {}", userRequest);
            }
        } else {
            log.error("Update doesn't contain any text message: {}", update);
        }
    }


    @Override
    public String getBotUsername() {
        // username which you give to your bot bia BotFather (without @)
        return botUsername;
    }

    @Override
    public String getBotToken() {
        // do not expose the token to the repository,
        // always provide it externally(for example as environmental variable)
        return botToken;
    }
}