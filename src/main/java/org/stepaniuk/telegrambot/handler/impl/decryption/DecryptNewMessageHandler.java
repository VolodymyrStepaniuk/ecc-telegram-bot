package org.stepaniuk.telegrambot.handler.impl.decryption;

import lombok.AllArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.enums.ConversationState;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.model.UserSession;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.stepaniuk.telegrambot.service.UserSessionService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
@AllArgsConstructor
public class DecryptNewMessageHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final UserSessionService userSessionService;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.decryption.new.message"));
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildReturnToMenu();
    telegramService.sendMessage(userRequest.getChatId(),
        messageResolver.getMessage("decryption.new.message"),
        replyKeyboardMarkup);
    UserSession session = userRequest.getUserSession();
    session.setState(ConversationState.WAITING_MESSAGE_FOR_DECRYPTION);
    userSessionService.saveSession(userRequest.getChatId(), session);
  }

  @Override
  public boolean isGlobal() {
    return false;
  }
}
