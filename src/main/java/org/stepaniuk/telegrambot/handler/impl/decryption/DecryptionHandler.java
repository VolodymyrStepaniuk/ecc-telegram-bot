package org.stepaniuk.telegrambot.handler.impl.decryption;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.stepaniuk.telegrambot.service.UserSessionService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
@AllArgsConstructor
public class DecryptionHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.decryption.message"));
  }

  @Override
  public void handle(UserRequest userRequest) {
    ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildDecryptionMenu();
    telegramService.sendMessage(userRequest.getChatId(),
        messageResolver.getMessage("below.menu_message"),
        replyKeyboardMarkup);
  }

  @Override
  public boolean isGlobal() {
    return true;
  }

}
