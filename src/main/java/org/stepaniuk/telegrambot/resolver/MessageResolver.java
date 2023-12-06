package org.stepaniuk.telegrambot.resolver;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

@RequiredArgsConstructor
public class MessageResolver {

  private final MessageSource messageSource;

  private final Locale defaultLocale;

  public String getMessage(String code) {
    return messageSource.getMessage(code, null, defaultLocale);
  }
}
