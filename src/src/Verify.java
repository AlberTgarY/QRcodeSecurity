
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
public class Verify {
    private static Crypt crypt =new Crypt();
    private static Object[] list = crypt.main();

    public static Object If_True(String Keyfilename,String textfilename)throws Exception{
        DESUtils DES= (DESUtils) list[0];
        RSAUtils RSA = (RSAUtils) list[1];
        String text = (String) list[2];
        //初始化两个bufferedReader
        BufferedReader br =getBufferReader(Keyfilename);
        BufferedReader br1 =getBufferReader(textfilename);
        String nextline = null;
        String nextline1 = null;
        //对密文（DES密匙）进行解密
        try {
            while ((nextline = br.readLine()) != null) {
                nextline1 = br1.readLine();
                //String deskey = RSA.decryptData(nextline.substring(7),nextline1.substring(7),false);//相当于服务器
                String plaintext = DES.getDecryptString(text,DES.getKey());
                System.out.println("  "+text+"   "+DES.getKey());
                System.out.println(plaintext);
            }
        }
        catch(NullPointerException e){
            System.out.println("NullPointerException!");
        }
        return list[3];

    }
    public static BufferedReader getBufferReader(String filename)throws Exception{
        FileInputStream fis = null; // 创建FileInputStream类对象读取File
        InputStreamReader isr = null; // 创建InputStreamReader对象接收文件流
        BufferedReader br = null;
        fis = new FileInputStream(filename);
        isr = new InputStreamReader(fis,"GBK");
        br = new BufferedReader(isr);
        return br;
    }
    public static void main(String[] args)throws Exception{

        QRCodeUtil QRcode = (QRCodeUtil) If_True("F:\\Work_Space\\JAVA DES\\Demo\\RSApriKey.txt","F:\\Work_Space\\JAVA DES\\Demo\\DESkey.txt");
        //System.out.println(QRcode.decode("689036325.jpg"));
        /**
        写一个能把二维码信息提取出来的函数，然后把密文中的加密DES密匙提出来,使用RSApubKey.txt中的所有RSA私钥依次进行解密，再将解密出来的密钥和当初
        生成二维码时存入xml文件内的密钥依次比较（tostring） 若有相同，则提取出相同的那个密钥，对明文进行解密.
         */



    }
}
