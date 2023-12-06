package org.stepaniuk.telegrambot.helper;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.stepaniuk.telegrambot.resolver.MessageResolver;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

/**
 * Helper class, allows to build keyboards for users
 */
@Component
@AllArgsConstructor
public class KeyboardHelper {

  private final MessageResolver messageResolver;

  public ReplyKeyboardMarkup buildMainMenu() {

    List<KeyboardRow> keyboardRows = new ArrayList<>();

    var buttons = createListOfButtons(
        List.of(messageResolver.getMessage("button.encryption.message"),
            messageResolver.getMessage("button.decryption.message"),
            messageResolver.getMessage("button.info.ecc.message"),
            messageResolver.getMessage("button.info.developer.message"))
    );

    buttons.forEach(button -> keyboardRows.add(new KeyboardRow(List.of(button))));

    return ReplyKeyboardMarkup.builder()
        .keyboard(keyboardRows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  public ReplyKeyboardMarkup buildDecryptionMenu() {

    List<KeyboardRow> keyboardRows = new ArrayList<>();

    var buttons = createListOfButtons(
        List.of(messageResolver.getMessage("button.decryption.old.message"),
            messageResolver.getMessage("button.decryption.new.message"))
    );

    buttons.forEach(button -> keyboardRows.add(new KeyboardRow(List.of(button))));

    return ReplyKeyboardMarkup.builder()
        .keyboard(keyboardRows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  public ReplyKeyboardMarkup buildReturnToMenu() {
    KeyboardRow keyboardRow = new KeyboardRow();
    keyboardRow.add(messageResolver.getMessage("button.return.to.menu.message"));

    return ReplyKeyboardMarkup.builder()
        .keyboard(List.of(keyboardRow))
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  private List<KeyboardButton> createListOfButtons(List<String> textInButtons) {
    var buttons = new ArrayList<KeyboardButton>();
    textInButtons.forEach(textInButton -> buttons.add(new KeyboardButton(textInButton)));
    return buttons;
  }
}
