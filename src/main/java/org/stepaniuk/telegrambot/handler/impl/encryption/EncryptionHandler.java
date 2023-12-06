package org.stepaniuk.telegrambot.handler.impl.encryption;

import java.security.KeyPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.enums.ConversationState;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.model.UserSession;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.ECCService;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.stepaniuk.telegrambot.service.UserSessionService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
@RequiredArgsConstructor
@Log4j2
public class EncryptionHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final UserSessionService userSessionService;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.encryption.message"));
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildReturnToMenu();
    KeyPair keyPair;
    UserSession session = userRequest.getUserSession();
    String message;
    try {
      keyPair = ECCService.generateKeyPair();
      session.setPublicKey(keyPair.getPublic());
      session.setPrivateKey(keyPair.getPrivate());
      session.setState(ConversationState.WAITING_MESSAGE_FOR_ENCRYPTION);
      message = messageResolver.getMessage("encryption.message");
    } catch (Exception e) {
      message = messageResolver.getMessage("error.message");
      log.error(e.getMessage(), e);
    }
    telegramService.sendMessage(userRequest.getChatId(),
        message,
        replyKeyboardMarkup);
    userSessionService.saveSession(userRequest.getChatId(), session);
  }

  @Override
  public boolean isGlobal() {
    return true;
  }

}
