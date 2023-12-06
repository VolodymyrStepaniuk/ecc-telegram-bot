package org.stepaniuk.telegrambot.handler.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.enums.ConversationState;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.model.UserSession;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.stepaniuk.telegrambot.service.UserSessionService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
@AllArgsConstructor
public class ReturnToMenuHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final UserSessionService userSessionService;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.return.to.menu.message"));
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
    telegramService.sendMessage(userRequest.getChatId(),
        messageResolver.getMessage("below.menu_message"), replyKeyboard);

    UserSession userSession = userRequest.getUserSession();
    userSession.setState(ConversationState.CONVERSATION_STARTED);
    userSessionService.saveSession(userSession.getChatId(), userSession);
  }

  @Override
  public boolean isGlobal() {
    return true;
  }
}
