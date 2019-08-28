
import java.util.Random;
    /**
     * 凯撒加密器： https://blog.csdn.net/xiaokui_wingfly/article/details/16338907
     * @author 小奎
     */
    public class Ceasar {
        int key;
        static StringBuffer plaintextStr = new StringBuffer("");
        static StringBuffer ciphertextStr = new StringBuffer("");
        final int max = 1024; // 最大字符

        public Object[] main(String text) {
            Ceasar m = new Ceasar();
            key=m.setKey();
            m.encryption(text);
            //m.deciphering(ciphertextStr.toString(),-1);
            m.display();
            Object[] list = {ciphertextStr,key};
            return list;
        }

        /**
         * 随机生成三位数密钥,返回偏移值
         *
         * @return
         */
        int setKey() {
            Random r = new Random();
            key = r.nextInt(900)+100;
            return key;
        }
        /**
         * 加密
         * @param plain 明文
         * @return
         */
        void encryption(String plain) {
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
        }

        /**
         * 解密
         * @param cipher,swift 密文，偏移量
         * @return StringBuffer
         */
        StringBuffer deciphering(String cipher,int swift) {
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

        /**
         * 显示对比结果
         */
        void display() {
            System.out.println("重排列成功！");
            System.out.println("密文：" + ciphertextStr);
        }


    }