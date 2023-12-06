package org.stepaniuk.telegrambot.helper;

import org.springframework.stereotype.Component;

@Component
public class DecryptionHelper {

  public byte[] convertToByteArray(String message) {
    message = message.substring(1, message.length()-1); // remove brackets
    String[] parts = message.split(", ");
    byte[] bytes = new byte[parts.length];
    for(int i = 0; i < parts.length; i++) {
      bytes[i] = Byte.parseByte(parts[i]);
    }
    return bytes;
  }
}
