
import org.springframework.util.StringUtils;
import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {

    /**
     * 加密（对外暴露）
     * 如果使用 公钥 对数据 进行加密，只有用对应的 私钥 才能 进行解密。
     * 如果使用 私钥 对数据 进行加密，只有用对应的 公钥 才能 进行解密。
     *
     * @param keyStr
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String keyStr, String data, Boolean isPublicKey) throws Exception {
        if (StringUtils.isEmpty(keyStr)) {
            return "";
        }
        return encryptBASE64(encrypt(getKey(keyStr, isPublicKey), data.getBytes()));
    }

    /**
     * 解密（对外暴露）
     * 如果使用 公钥 对数据 进行加密，只有用对应的 私钥 才能 进行解密。
     * 如果使用 私钥 对数据 进行加密，只有用对应的 公钥 才能 进行解密。
     *
     * @param keyStr
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptData(String keyStr, String data, Boolean isPublicKey) throws Exception {
        if (StringUtils.isEmpty(keyStr)) {
            return "";
        }
        return new String(decrypt(getKey(keyStr, isPublicKey), decryptBASE64(data)), "UTF-8");
    }

    /**
     * 加密
     *
     * @param key
     * @param srcBytes
     * @return
     */
    private static byte[] encrypt(Key key, byte[] srcBytes) {
        if (key != null) {
            try {
                //Cipher负责完成加密或解密工作，基于RSA
                Cipher cipher = Cipher.getInstance("RSA");
                //对Cipher对象进行初始化
                cipher.init(Cipher.ENCRYPT_MODE, key);
                //加密，并返回
                return cipher.doFinal(srcBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key
     * @param encBytes
     * @return
     */
    private static byte[] decrypt(Key key, byte[] encBytes) {
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                //对Cipher对象进行初始化
                cipher.init(Cipher.DECRYPT_MODE, key);
                //解密并返回结果
                return cipher.doFinal(encBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 根据key获取公有或者私有key对象
     *
     * @param keyStr
     * @param isPublicKey
     * @return
     * @throws Exception
     */
    private static Key getKey(String keyStr, Boolean isPublicKey) throws Exception {
        if (isPublicKey) {
            return getPublicKey(keyStr);
        } else {
            return getPrivateKey(keyStr);
        }
    }

    /**
     * 根据公有key获取公有key对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * 根据私有key获取私有对象
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公有/私有Key
     *
     * @return
     */
    private static KeyPair getRSAKey() {
        KeyPair keyPair = null;
        try {
            //生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            //初始化密钥对生成器，密钥大小为1024位
            keyPairGen.initialize(1024);
            //生成一个密钥对，保存在keyPair中
            keyPair = keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    /**
     * 对字符串进行BASE64Decoder
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * 对字节数组进行BASE64Encoder
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static String encryptBASE64(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }

    public String  main(String message) {
        // 生成的一对key保存好
        try {
            String fileName = "F:\\Work_Space\\JAVA DES\\Demo\\RSApubpriKeypair.txt";//String类型密匙文件路
            //得到私钥和公钥
            KeyPair keyPair = getRSAKey();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            FileOutputStream fos = new FileOutputStream(fileName,true);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            String pubKey = encryptBASE64(publicKey.getEncoded());
            String priKey = encryptBASE64(privateKey.getEncoded());
            /**
            System.out.println("公钥：" + pubKey);
            System.out.println("私钥：" + priKey);
             */
            String jiami = encryptData(pubKey, message, true);//公钥加密后的密文

            oos.writeObject("PUB KEY:  ");
            oos.writeObject(pubKey);
            oos.writeObject("PRI KEY:  ");
            oos.writeObject(priKey);
            oos.close();
            // 测试

            System.out.println("明文：" + message);
            //String jiami = encryptData(pubKey, message, true);
            System.out.println("公钥加密后：" + jiami);
            String jiemi = decryptData(priKey, jiami, false);
            System.out.println("用私钥解密后的结果是:" + jiemi);

            String pjiami = encryptData(priKey, message, false);
            System.out.println("私钥加密后：" + pjiami);
            String pjiemi = decryptData(pubKey, pjiami, true);
            System.out.println("用公钥解密后的结果是:" + pjiemi);

            return jiami;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}