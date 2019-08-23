
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;


public class DESUtils {
    private static RSAUtils RSA = new RSAUtils();
    private static Key key;
    private static String codedKey;
    static {
        try {
            String fileName1 = "F:\\Work_Space\\JAVA DES\\Demo\\DESkey.txt";//String类型密匙文件路径
            String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\DesKey.xml";
            //将DES的密匙用RSA加密
            SecureRandom sr = new SecureRandom();
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(sr);
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            FileOutputStream fos1 = new FileOutputStream(fileName1,true);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            // 生成密钥
            key = generator.generateKey();//用于DES加密的原密匙
            codedKey=RSA.main(key.toString());
            oos.writeObject(key);
            oos.close();
            oos1.writeObject(" "+codedKey+"\r\n");//此处存放着RSA加密后的DES密匙（RSA公钥密文）
            oos1.close();
            generator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取key
     * @return
     */
    public Key getKey() {
        Key kp = null;
        try {
            // 相对路径 需要新建 conf 文件夹
            String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\DesKey.xml";
            FileInputStream is = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(is);
            kp = (Key) oos.readObject();
            oos.close();
            // 绝对路径
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }

    /**
     * 加密，返回BASE64的加密字符串
     * @param str
     * @return
     */
    public static String getEncryptString(String str,Key key) throws Exception {
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
    public  String getDecryptString(String str,Key key) throws Exception {
        byte[] strBytes = Base64.getDecoder().decode(str);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptStrBytes = cipher.doFinal(strBytes);
        return new String(encryptStrBytes, "UTF-8");
    }
    public String[] get_encryname_key_pair(String name)throws Exception{
        String[] list = {getEncryptString(name,key),codedKey};
        return list;
    }

    public RSAUtils main() {
        /**
        String encryname = getEncryptString(name);
        System.out.println("加密：" + encryname);
        System.out.println("解密：" + getDecryptString(encryname));
         */
        return RSA;
    }

}