package org.stepaniuk.telegrambot.resolver;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceConfig {
  @Bean
  public MessageSource messageSource() {
    var source = new ResourceBundleMessageSource();
    source.setBasenames("messages");
    source.setDefaultEncoding("UTF-8");
    source.setDefaultLocale(Locale.forLanguageTag("ua"));
    return source;
  }
  @Bean
  public MessageResolver telegramMessageResolver(MessageSource messageSource) {
    return new MessageResolver(messageSource, Locale.forLanguageTag("ua"));
  }
}
