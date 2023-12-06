package org.stepaniuk.telegrambot.handler.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


@Component
@AllArgsConstructor
public class InfoAboutECCHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isTextMessage(userRequest.getUpdate(),
        messageResolver.getMessage("button.info.ecc.message"));
  }

  @Override
  public void handle(UserRequest request) {
    ReplyKeyboard replyKeyboard = keyboardHelper.buildReturnToMenu();
    telegramService.sendMessage(request.getChatId(),
        messageResolver.getMessage("info.ecc.message"),
        replyKeyboard);
  }

  @Override
  public boolean isGlobal() {
    return true;
  }
}
