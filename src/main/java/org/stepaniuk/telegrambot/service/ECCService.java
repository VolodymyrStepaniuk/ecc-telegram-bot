package org.stepaniuk.telegrambot.service;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class ECCService {
  static {
    // Додавання Bouncy Castle Provider
    Security.addProvider(new BouncyCastleProvider());
  }

  public static KeyPair generateKeyPair() throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
    keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
    return keyPairGenerator.generateKeyPair();
  }

  public static byte[] encryptData(String data, PublicKey publicKey) throws Exception {
    Cipher cipher = Cipher.getInstance("ECIES", "BC");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    return cipher.doFinal(data.getBytes());
  }

  public static String decryptData(byte[] encryptedData, PrivateKey privateKey) throws Exception {
    Cipher cipher = Cipher.getInstance("ECIES", "BC");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);
    byte[] decryptedBytes = cipher.doFinal(encryptedData);
    return new String(decryptedBytes);
  }
}
