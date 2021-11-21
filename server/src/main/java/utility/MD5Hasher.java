package utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MD5Hasher {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String hash(String value){
        byte[] bytes = md.digest(value.getBytes());
        //byte[] bytes = value.getBytes();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: bytes){
            stringBuilder.append(String.format("%02X", b));
        }
       return stringBuilder.toString();
    }

}
