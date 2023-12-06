package org.stepaniuk.telegrambot.handler.impl.decryption;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Log4j2
public class DecryptOldMessageHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final UserSessionService userSessionService;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.decryption.old.message"));
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMainMenu();

    UserSession session = userRequest.getUserSession();
    String message;
    try {
      var decryptedMessage = ECCService.decryptData(session.getEncryptedMessage(),
          session.getPrivateKey());
      message = messageResolver.getMessage("decrypted.old.message") + " " + decryptedMessage;
      session.setState(ConversationState.CONVERSATION_STARTED);
    } catch (Exception e) {
      message = messageResolver.getMessage("decryption.old.error");
      log.error("Error while decrypting old message", e);
    }
    telegramService.sendMessage(userRequest.getChatId(),
        message, replyKeyboardMarkup);
    userSessionService.saveSession(userRequest.getChatId(), session);
  }

  @Override
  public boolean isGlobal() {
    return false;
  }
}
