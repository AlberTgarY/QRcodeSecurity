
import java.io.BufferedReader;
import java.io.FileInputStream;
import javax.crypto.BadPaddingException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.io.File;

/**
 * 此class负责控制二维码生成以及验证,
 * 此Demo预先生成了若干二维码在文件夹Gen内，若想重新生成请删除二维码以及RSApriKey.txt /RSApubKey.txt /DesKey.txt内的所有内容.
 * 2019-8-27 Albert
 */
/**
* this class is responsible for controlling the generation and verification of the qr code.
* this Demo pre-generated several qr codes in the folder Gen, please delete the qr code and all contents in rsaprikey. TXT/rsapubkey. TXT/deskey. TXT if you want to regenerate.
* the 2019-8-27 Albert
 */
public class Verify {

    public static DESUtils DES= new DESUtils();
    public static RSAUtils RSA = new RSAUtils();
    public static Ceasar ceasar = new Ceasar();
    /**
     * Generate num numbers of QRcode
     * 生成num个二维码
     * @param  num numbers of QRcode generated.
     * @return void
     */
    public static void getQRcode(int num)throws Exception{
        if(num>0) {
            for (int i = 0; i < num; i++) {
                Crypt crypt = new Crypt();
                crypt.main();
            }
        }
    }
    /**
     * 判断当前选中二维码是否符合防伪标准
     * 当前使用记事本充当服务器.
     * Keyfilename:储存RSA私钥(服务器端)
     * Textfilename:储存加密后的DES密匙(服务器端)
     * @param Keyfilename 私钥文件路径
     * @param Textfilename  密匙文件路径
     * @param filename 解密二维码文件名
     * @return boolean
     */
    /**
    * determine whether the selected qr code conforms to anti-counterfeiting standards
    * currently using notepad as the server.
     * Keyfilename: stores RSA private key (server-side)
    * Textfilename: stores encrypted DES keys (server-side)
    * @param Keyfilename private key file path
    * @param Textfilename key file path
    * @param filename
    * @ return Boolean
    */
    public static boolean
    If_True(String Keyfilename,String Textfilename,String filename)throws Exception{
        //初始化两个bufferedReader
            BufferedReader br = getBufferReader(Keyfilename);
            BufferedReader br1 = getBufferReader(Textfilename);
            String nextline;
            String nextline1;
            String deskey = null;
        //对密文（DES密匙）进行RSA解密
        try {
            QRCodeUtil QRcode = new QRCodeUtil();

            String QRcodetext = QRcode.decode(filename);
            System.out.println("Successfully read the QRcode！");
            int num=0;
            int i=0;
            for(;i<QRcodetext.length();i++) {
                char c=QRcodetext.charAt(i);
                if(c=='\r'){break;}
            }
            String text = QRcodetext.substring(0,i);
            String codedKey = QRcodetext.substring(i+2);
            System.out.println("Encrypted text:  "+text);
            System.out.println("Encrypted key:   "+codedKey);
            System.out.println("-------------------------------------------------------------------------");
         //   System.out.println("是否使用DES加密并且使用RSA加密DES密匙？若是请输入Yes,若不是请输入任意字符跳过...");
            System.out.println("Are you trying to use RSA to encrypt the text, and use RSA to encrypt DES key？If so enter Yes, If not press any button to skip...");
            Scanner scan = new Scanner(System.in);
            if(scan.nextLine().equalsIgnoreCase("YES")){
              //  System.out.println("----------------------正在使用RSA解密二维码密匙----------------------------");
                System.out.println("----------------------Using RSA to encrypt QRcode Key----------------------------");
                while ((nextline = br.readLine()) != null) {
                    if (RSAdecrypt(nextline.substring(7), codedKey)) {
                        deskey = RSA.decryptData(nextline.substring(7), codedKey, false);//相当于服务器
                    //    System.out.println("----->解密成功！  密匙为:  " + deskey);
                        System.out.println("----->Encryption success！  The Key is:  " + deskey);
                        num=-1;
                        System.out.println("-------------------------------------------------------------------------");
                        break;
                  //  } else { num+=1;System.out.println("----->解密失败！使用记事本中第"+num+"个密匙"); }
                    } else { num+=1;System.out.println("----->Encryption failed！Using key No."+num+" in the text file."); }
                }
            }
            else{
           //     System.out.println("是否由DES加密二维码？若是请输入Yes,若由RSA加密请输入任意字符跳过...");
                System.out.println("Use DES to encrypt？If so enter Yes,if using RSA to encrypt pls press any button to skip...");
                Scanner scan1 = new Scanner(System.in);
                if(scan1.nextLine().equalsIgnoreCase("YES")){
                     deskey=codedKey;
                }
                else{
                   // System.out.println("------------------------------正在使用RSA解密----------------------------");
                    System.out.println("------------------------------Using RSA to encrypt QRcode----------------------------");
                    num=0;
                    codedKey = QRcodetext.substring(0,i);
                    while ((nextline = br.readLine()) != null) {
                        if (RSAdecrypt(nextline.substring(7), codedKey)) {
                            deskey = RSA.decryptData(nextline.substring(7), codedKey, false);//相当于服务器
                            System.out.println("----->Encryption success！  The Key is:  " + deskey);
                            String ciphertext = deskey.substring(0,deskey.length()-3);
                            String swift = deskey.substring(deskey.length()-3);
                            StringBuffer jiemi = ceasar.deciphering(ciphertext,Integer.parseInt(swift));
                            System.out.println("----->Rearranging Success！The plainText:  " + jiemi.toString());
                            System.out.println("-------------------------------------------------------------------------");
                            return true;
                        } else { num+=1;System.out.println("----->Encryption failed！Using key No."+num+" in the text file."); }
                    }

                }
            }
           // System.out.println("-------------------------------正在匹配DES密匙----------------------------");
            System.out.println("-------------------------------Matching DES keys----------------------------");
            //若密匙成功解密，则使用此密匙对加密密文进行DES解密.
           while ((nextline1=br1.readLine())!=null){
                if(deskey.equals(nextline1.substring(7))){
                    System.out.println("-------------------------------------------------------------------------");
                  //  System.out.println("密匙成功匹配！开始进行DES解密..." );
                    System.out.println("Keys matched successfully！Starting DES encryption..." );
                    String plaintext = DESdecrypt(text,deskey);
                    System.out.println("----->Encryption success！  The Key is:  " + plaintext);
                    //根据偏移量对明文前半部分进行重排列.
                    String ciphertext = plaintext.substring(0,plaintext.length()-3);
                    String swift = plaintext.substring(plaintext.length()-3);
                    StringBuffer jiemi = ceasar.deciphering(ciphertext,Integer.parseInt(swift));
                    System.out.println("----->Relining Success！The plainText:  " + jiemi.toString());
                    System.out.println("-------------------------------------------------------------------------");
                    return true;
                }
                else { num+=1;System.out.println("----->Encryption failed！Using key No."+num+" in the text file."); }
            }
        }
        catch(IOException e){
            System.out.println("Wrong file path!");
        }
        catch(NullPointerException e){
            System.out.println("NullPointerException");
        }
        return false;
    }

    /**
     * DES decrypting unit
     * @param text
     * @param key
     * @return String
     */
    private static String DESdecrypt(String text,String key){
        try {

           String plaintext= DES.getDecryptString(text,key);
            return plaintext;
        }
        catch(Exception e){
            System.out.println("Decryption failed！");
            return null;
        }
    }
    /**
     * RSA decrypting unit
     * @param text
     * @param key
     * @return boolean
     */
    private static boolean RSAdecrypt(String key,String text){
        try {
            RSAUtils RSA = new RSAUtils();
            RSA.decryptData(key, text, false);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    /**
     * Generate BufferReader, read current file
     * @param filename
     * @return BufferedReader
     * @throws Exception
     */
    public static BufferedReader getBufferReader(String filename)throws Exception{
        FileInputStream fis; // 创建FileInputStream类对象读取File
        InputStreamReader isr; // 创建InputStreamReader对象接收文件流
        BufferedReader br;
        fis = new FileInputStream(filename);
        isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        br = new BufferedReader(isr);
        return br;
    }
    /**
     * Determine whether it is fake.
     * @param keyfilename,Textfilename,filename
     * @return void
     * @throws Exception
     */

    private static void verify(String keyfilename,String Textfilename,String filename)throws Exception{ ;
        Boolean if_true = If_True(keyfilename,Textfilename,filename);
        if(if_true){
            System.out.println("----->Your product is legit.");
        }
        else{
            System.out.println("----->Your product is fake.");
        }
    }
    /**
     * 如要生成二维码，请注释掉//验证二维码后的部分.
     * 如需验证二维码，请注释掉//生成二维码后的部分，并在第三个parameter内修改二维码文件名.
     * @param args
     * @throws Exception
     */
    /**
    * to generate the qr code, please comment out Verify()
    * to verify the qr code, please comment out getQRcode() , and modify the file name of the qr code in the third parameter in Verify().
    * @ param args
    * @ throws the Exception
    */
    public static void main(String[] args)throws Exception{
        //Generate QRcode
        //getQRcode(1);
        //Verify QRcode
        /**
         * **************************************************************************************************************************
         * 请在此处添加您想要验证的QRcode文件名.
         * 当前生成二维码后存放文件路径为"F:\\Work_Space\\JAVA DES\\Gen",可以根据需要在QRCodeUtil.java内更改.
         * **************************************************************************************************************************
         */
        /**
         * ***************************************************************************************************************************
         * add the QRcode file name you want to verify here.
         * the current path to store the file after generating the qr code is "F:\ Work_Space\\JAVA DES\\Gen", which can be changed in qrcodeutil.java as required.
         * ***************************************************************************************************************************
         * */
        File directory = new File(".");
        String path = directory.getCanonicalPath();
        //verify(path+"\\Demo\\RSApriKey.txt",path+"\\Demo\\DesKey.txt","91171272.jpg");
        verify(path+"\\Demo\\RSApriKey.txt",path+"\\Demo\\DesKey.txt","492635395.jpg");
    }
}
