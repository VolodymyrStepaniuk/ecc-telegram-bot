package org.stepaniuk.telegrambot.handler.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.enums.ConversationState;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

@Component
@AllArgsConstructor
public class ErrorHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate())
        && ConversationState.ERROR_OCCURRED.equals(
        userRequest.getUserSession().getState());
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
    telegramService.sendMessage(userRequest.getChatId(),
        messageResolver.getMessage("error.message"),
        replyKeyboard);
  }

  @Override
  public boolean isGlobal() {
    return false;
  }
}
