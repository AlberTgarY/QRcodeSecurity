
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 此class负责进行二维码DES加密
 * 2019-8-28 Albert
 */
public class DESUtils {
    private static RSAUtils RSA = new RSAUtils();
    private SecretKey securekey;
    private String key;
    private String codedKey;
    /**
     * 对字符串进行BASE64Decoder
     *
     * @param key
     * @return
     * @throws Exception
     */
    private byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 对字节数组进行BASE64Encoder
     *
     * @param key
     * @return
     * @throws Exception
     */
    private String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    /**
     * 获取key
     * @return
     */
    public String getKey(){
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            // 生成一个Key
            securekey = keyGenerator.generateKey();
            // 转变为字节数组
            byte[] encoded = securekey.getEncoded();
            // 生成密钥字符串
            String encodeString = encryptBASE64(encoded);
            return encodeString;
        } catch (Exception e) {
            e.printStackTrace();
            return "密钥生成错误.";
        }
    }
    /**
     * 加密，返回BASE64的加密字符串
     * @param str,key 当key是String类型时使用
     * @return
     */
    public  String getEncryptString(String str,String key) throws Exception {
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        securekey = keyFactory.generateSecret(desKey);
        byte[] strBytes = str.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return encryptBASE64(encryptStrBytes);
    }
    /**
     * 加密，返回BASE64的加密字符串
     * @param str,key 当key是SecretKey类型时使用
     * @return
     */
    public  String getEncryptString(String str,SecretKey key) throws Exception {
        byte[] strBytes = str.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return encryptBASE64(encryptStrBytes);
    }


    /**
     * 对BASE64加密字符串进行解密
     * @param str
     * @return
     */
    public  String getDecryptString(String str,String key) throws Exception {
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        securekey = keyFactory.generateSecret(desKey);
        byte[] strBytes = decryptBASE64(str);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return new String(encryptStrBytes, "UTF-8");
    }

    /**
     * 得到DES加密后的密文，以及经过RSA加密后的DES密匙.
     * @param name
     * @return String[] 储存密文以及加密后的密匙.
     * @throws Exception
     */
    public String[] get_encryname_key_pair(String name)throws Exception{
        String[] list = {getEncryptString(name,key),codedKey};
        return list;
    }

    /**
     * main()是DESUtils的主要单元，负责加密DES密匙，生成DES密匙，并写入相应的txt文件内方便验证时提取。
     * @param
     * @return
     */
    public void main() {
            try {
               //String类型密匙文件路径
                String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\DesKey.txt";
                //根据文件路径读取文件
                FileOutputStream fos = new FileOutputStream(fileName,true);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                // 生成密钥
                key = getKey();//用于DES加密的原密匙
                /*测试
                String jiami = getEncryptString("Hello World!",key);
                String jiami1 = getEncryptString("Hello World!",securekey);
                System.out.println("密匙:" + key);
                System.out.println("加密后：" + jiami+"以及"+jiami1);
                String jiemi = getDecryptString(jiami,key);
                System.out.println("解密后：" + jiemi);
                System.out.println("--------------------------------------------------------------");
                 */
                //将des密匙使用rsa加密.
                codedKey=RSA.main(key);
                oos.writeObject(key+"\r\n");
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}