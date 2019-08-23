
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
class Encrypt {

    public static void saveDesKey() {
        try {
            SecureRandom sr = new SecureRandom();
            // 为我们选择的DES算法生成一个KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);
            // 相对路径 需要新建 conf 文件夹
            // String fileName = "conf/DesKey.xml";
            // 绝对路径
            String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\DesKey.xml";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // 生成密钥
            Key key = kg.generateKey();
            oos.writeObject(key);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Key getKey() {
        Key kp = null;
        try {
            // 相对路径 需要新建 conf 文件夹
            // String fileName = "conf/DesKey.xml";
            // InputStream is = Encrypt.class.getClassLoader().getResourceAsStream(fileName);
            // 绝对路径
            String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\DesKey.xml";
            FileInputStream is = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(is);
            kp = (Key) oos.readObject();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kp;
    }

    public static void encrypt(String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherInputStream cis = new CipherInputStream(is, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    public static void decrypt(String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(dest);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = is.read(buffer)) >= 0) {
            cos.write(buffer, 0, r);
        }
        cos.close();
        out.close();
        is.close();
    }
}
public class DES {
    public static void main(String[] args) throws Exception {
        Encrypt.saveDesKey();
        System.out.println("生成key");
        Encrypt.getKey();
        System.out.println("获取key");
        Encrypt.encrypt("F:\\Work_Space\\JAVA DES\\material\\hello.txt", "F:\\Work_Space\\JAVA DES\\material\\encrypt.txt");
        System.out.println("加密");
        Encrypt.decrypt("F:\\Work_Space\\JAVA DES\\material\\encrypt.txt", "F:\\Work_Space\\JAVA DES\\material\\decrypt.txt");
        System.out.println("解密");
    }
}