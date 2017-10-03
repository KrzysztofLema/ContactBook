package pl.Krzysiek.testProject.models;

import javafx.scene.control.Alert;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    public static void createSimpleDialog(String name, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(name);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public static String shaHash(String message) {
        try {
            MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
            byte[] bytesOfMessage = message.getBytes();
            byte[] bytesOfCryptoMessage = sha2.digest(bytesOfMessage);
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i <= bytesOfCryptoMessage.length-1; i++) {
                stringBuilder.append(Integer.toHexString(0xFF & bytesOfCryptoMessage[i]));
            }
            return stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

