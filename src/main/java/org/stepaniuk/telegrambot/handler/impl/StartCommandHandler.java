package org.stepaniuk.telegrambot.handler.impl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.handler.UserRequestHandler;
import org.stepaniuk.telegrambot.helper.KeyboardHelper;
import org.stepaniuk.telegrambot.model.UserRequest;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.stepaniuk.telegrambot.service.TelegramService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;


@Component
@AllArgsConstructor
public class StartCommandHandler extends UserRequestHandler {

  private final TelegramService telegramService;
  private final KeyboardHelper keyboardHelper;
  private final MessageResolver messageResolver;

  @Override
  public boolean isApplicable(UserRequest userRequest) {
    return isCommand(userRequest.getUpdate(),
        messageResolver.getMessage("start.command"));
  }

  @Override
  public void handle(UserRequest request) {
    ReplyKeyboard replyKeyboard = keyboardHelper.buildMainMenu();
    telegramService.sendMessage(request.getChatId(),
        messageResolver.getMessage("start.message"),
        replyKeyboard);
    telegramService.sendMessage(request.getChatId(),
        messageResolver.getMessage("below.menu_message"));
  }

  @Override
  public boolean isGlobal() {
    return true;
  }
}
