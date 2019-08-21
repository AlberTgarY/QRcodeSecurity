
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;


public class DESUtils {
    private static RSAUtils RSA = new RSAUtils();
    private static Key key;
    private static String PRIVATE_KEY = "123";
    private static String RSA_PubKey;

    static {
        try {
            //将DES的密匙用RSA加密
            String[] list =RSA.main(PRIVATE_KEY);
            PRIVATE_KEY = list[0];
            RSA_PubKey = list[1];
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(PRIVATE_KEY.getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密，返回BASE64的加密字符串
     * @param str
     * @return
     */
    public static String getEncryptString(String str) throws Exception {
        byte[] strBytes = str.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return Base64.getEncoder().encodeToString(encryptStrBytes);
    }

    /**
     * 对BASE64加密字符串进行解密
     * @param str
     * @return
     */
    public static String getDecryptString(String str) throws Exception {
        byte[] strBytes = Base64.getDecoder().decode(str);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return new String(encryptStrBytes, "UTF-8");
    }
    public String get_encryname(String name)throws Exception{
        return getEncryptString(name);
    }
    public String getEncryptedDESkeyByRSAPubkey(){
        return PRIVATE_KEY;
    }
    public String getRSAPubkey(){
        return RSA_PubKey;
    }
    public void main(String name) throws Exception {
        String encryname = getEncryptString(name);
        System.out.println("加密：" + encryname);
        System.out.println("解密：" + getDecryptString(encryname));
    }

}