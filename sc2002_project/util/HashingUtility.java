package sc2002_project.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class HashingUtility {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error: Unable to hash password.", e);
        }
    }


    public static boolean verifyPassword(String inputPassword, String storedHash, String storedSalt) {
        if (inputPassword == null || storedHash == null || storedSalt == null) {
            return false;
        }
        String inputHash = hashPassword(inputPassword, storedSalt);
        return inputHash.equals(storedHash);
    }
}