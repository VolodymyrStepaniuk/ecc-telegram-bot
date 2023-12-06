package org.stepaniuk.telegrambot.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.Builder;
import lombok.Data;
import org.stepaniuk.telegrambot.enums.ConversationState;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;
    private byte[] encryptedMessage;
    private PublicKey publicKey;
    private PrivateKey privateKey;
}
