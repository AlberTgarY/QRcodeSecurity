import java.util.Random;
    /**
     * Ceasar Encryption： https://blog.csdn.net/xiaokui_wingfly/article/details/16338907
     * @author 小奎
     */
    public class Ceasar {
        private  int key;
        private final int max = 1024; // 最大字符

        public Object[] main(String text) {
            StringBuffer ciphertextStr;
            Ceasar c = new Ceasar();
            key=c.setKey();
            ciphertextStr=c.encryption(text);
            //m.deciphering(ciphertextStr.toString(),-1);
            Object[] list = {ciphertextStr,key};
            return list;
        }

        /**
         * 随机生成三位数密钥,返回偏移值
         *
         * @return
         */
       private int setKey() {
            Random r = new Random();
            key = r.nextInt(900)+100;
            return key;
        }
        /**
         * Encrypting
         * @param plain 明文
         * @return
         */
        StringBuffer encryption(String plain) {
            StringBuffer ciphertextStr = new StringBuffer("");
            char[] plaintext = plain.toCharArray();
            char[] ciphertext = new char[max];
            for (int j = 0; j < max; j++) {
                ciphertext[j] = '★'; // 设置临时变量将数组填充，因明文中可存在' '空，所以需要填充判断
            }
            for (int i = 0; i < plaintext.length; i++) {
                if (plaintext[i] != '★') {
                    int temp = plaintext[i] + key;    // 偏移后的ASCII码
                    ciphertext[i] = (char) temp; // 加密符号
                    ciphertextStr.append(ciphertext[i]); // 拼接字符串
                } else {
                    break;
                }
            }
            return ciphertextStr;
        }

        /**
         * Decrypting
         * @param cipher,swift 密文，偏移量
         * @return StringBuffer
         */
        StringBuffer deciphering(String cipher,int swift) {
            StringBuffer plaintextStr = new StringBuffer("");
            if(swift==-1){
                swift =key;
            }
            char[] ciphertext = cipher.toCharArray();
            char c = ' ';
            for (int i = 0; i < ciphertext.length; i++) {
                if (ciphertext[i] != '★') {
                    int temp = ciphertext[i] - swift;
                    c = (char) temp;
                    plaintextStr.append(c);// 拼接解密字符串

                } else {
                    break;
                }
            }
            return plaintextStr;
        }


    }