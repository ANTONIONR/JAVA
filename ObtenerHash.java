--  ----------------------------------------------------------------------------------------------------
--
--  FILE NAME      : ObtenerHash.java
--  DESCRIPTION    : Get the password in hash for Oracle ( all versions <= 11g )
--  AUTHOR         : Antonio NAVARRO - /\/\/
--  CREATE         : 28.10.15
--  LAST MODIFIED  : 28.10.15
--  USAGE          : This script inputs two parameters. Parameter 1 is the username
--                                                      Parameter 2 is the password
--                   f (username, password) -> hash
--  CALL SYNTAXIS  : java ObtenerHash <username> <password>
--  [NOTES]        :
--   
--  ----------------------------------------------------------------------------------------------------


import java.util.Arrays;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* java ObtenerHash usuario password */
public class ObtenerHash {

  private static final byte[] keyBytes = {
    (byte) 0x01,
    (byte) 0x23,
    (byte) 0x45,
    (byte) 0x67,
    (byte) 0x89,
    (byte) 0xab,
    (byte) 0xcd,
    (byte) 0xef
  };

  private static final IvParameterSpec CBC = new IvParameterSpec(new byte[8]);

  public static void main(String[] args) throws Exception {
    System.out.println(GeneraHash(args[0], args[1]));
  }

  public static String GeneraHash(String user, String password) throws Exception {
    byte[] TextoEnClaro = (user + password).toUpperCase().getBytes("utf-16be");
    TextoEnClaro = Arrays.copyOf(TextoEnClaro, ((TextoEnClaro.length + 7) / 8) * 8);
    Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
    SecretKey vuelta1 = new SecretKeySpec(keyBytes, "DES");
    cipher.init(Cipher.ENCRYPT_MODE, vuelta1, CBC);
    byte[] encryptedBytes = cipher.doFinal(TextoEnClaro);
    Key vuelta2 = new SecretKeySpec(encryptedBytes, encryptedBytes.length - 8, 8, "DES");
    cipher.init(Cipher.ENCRYPT_MODE, vuelta2, CBC);
    byte[] clave_hash = cipher.doFinal(TextoEnClaro);
    return ObtenHexa(clave_hash, clave_hash.length - 8, 8);
  }

  private static String ObtenHexa(byte[] bytes, int offset, int length) {
    final StringBuilder CadenaHexa = new StringBuilder();
    for (int i = 0; i < length; i++) {
        CadenaHexa.append(String.format("%02X", bytes[offset++]));
    }
    return CadenaHexa.toString();
  }

}




