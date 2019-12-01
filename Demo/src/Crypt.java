import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
public class Crypt {
    private DESUtils DES = new DESUtils();
    private RSAUtils RSA = new RSAUtils();
    private QRCodeUtil QRCode = new QRCodeUtil();
    private Ceasar ceasar = new Ceasar();
    public String text;
    /**
     * 此class负责进行二维码加密及输出,
     * 2019-8-28 Albert
     */
    /**
     * This class is responsible for the encrypting and outputing of the QR code,
     * 2019-8-28 Albert
     */
    public Crypt() {
        text = "Albert";
        //System.out.println("是否使用DES加密二维码并且使用RSA加密DES密匙？ 若需要请输入Yes,若仅使用DES或RSA加密二维码请输入任意字符跳过...");
        System.out.println("Use DES and RSA to encrypt? If so please type yes. If only use either DES or RSA encrpting, type anything to skip...");
        Scanner Scan = new Scanner(System.in);
        if(Scan.nextLine().equalsIgnoreCase("YES")){
            //System.out.println("----->使用RSA加密DES密匙.");
            System.out.println("----->Use RSA to encrypt DES key.");
            DES.main(true);
        }
        else{
            System.out.println("----->不使用RSA加密DES密匙.");
            System.out.println("----->Not to use RSA to encrypt DES key.");
            DES.main(false);
        }
    }
    /**
     * 加密部分
     * @return String 加密后的text
     * @throws Exception
     */
     /**
     * Encrypting part
     * @return String encrypted text
     * @throws Exception
     */
    public String  crypt() throws Exception{
        try {
            //System.out.println("是否使用DES加密二维码？若是请输入Yes,若使用RSA加密请输入任意字符跳过...");
            System.out.println("Use DES to encrpt QR code? If so please type Yes, otherwise, type anything to apply RSA");
            Scanner scan = new Scanner(System.in);
            if (scan.nextLine().equalsIgnoreCase("YES")) {

                //System.out.println("-------------------------DES加密,正在对明文进行重排列--------------------------");
                System.out.println("-------------------------DES encrypting and rearranging the plain text--------------------------");
                /**
                 * 请在此处修改您想要生成二维码的信息.
                 */
                /**
                 * Please modify the text that is going to be generated to QR code.
                 */
                Object[] list;
                /**
                 * comment text = generateStr(text, 9); if you dont want to generate random serial numbers.
                 */
                //生成random serial number
                //generate random serial number
                //text = generateStr(text, 9);
                //对明文进行重排列
                //rearranging the plain text
                list = ceasar.main(text);
                //密文
                //Encrypted text
                StringBuffer cipher = (StringBuffer) list[0];
                //偏移量
                //shifting
                int swift = (int) list[1];
                //System.out.println("------------------------------正在生成二维码----------------------------------");
                System.out.println("------------------------------Generating QR code----------------------------------");
                String[] pair_list = DES.get_encryname_key_pair(cipher.toString() + swift);
                String encryname = pair_list[0];
                String key = pair_list[1];
                //得到加密明文
                //Get the plain text
                //System.out.println("加密明文:" + encryname);
                System.out.println("Encrypted text:" + encryname);
                //System.out.println("DES密匙:" + key);
                System.out.println("DES key:" + key);
                //生成加密二维码
                //generate encrypted QR code
                QRCode.main(encryname + "\r\n" + key, true);
                //System.out.println("加密二维码已经生成！");
                System.out.println("Encrpted QR code is generated!");
                System.out.println("-----------------------------------------------------------------------------");
                return encryname;
            } else {
                //System.out.println("----------------------RSA加密,正在对明文进行重排列----------------------------");
                System.out.println("----------------------RSA Encrypting and rearranging the plain text----------------------------");
                //生成明文二维码
                //Generaing plain QR code
                //String filename= QRCode.main("My name is Zyc", true);
                //System.out.println("明文二维码已经生成！");
                //String text = QRCode.decode(filename);//提取明文
                /**
                 * 请在此处修改您想要生成二维码的信息.
                 */
                /**
                 * Please modify the text that is going to be encrpted
                 */
                Object[] list;
                //text = generateStr(text, 9);
                //对明文进行重排列
                //rearranging
                list = ceasar.main(text);
                //密文
                //Encrypting text
                StringBuffer cipher = (StringBuffer) list[0];
                //偏移量
                //shifting
                int swift = (int) list[1];
                //System.out.println("------------------------------正在生成二维码----------------------------------");
                System.out.println("------------------------------Generating QR code----------------------------------");
                String encrytext = RSA.main(cipher.toString() + swift);
                //得到加密明文
                //Get the encrypted text
                //System.out.println("加密明文:" + encrytext);
                System.out.println("Encrpted text:" + encrytext);
                //生成加密二维码
                //Generating QR code
                QRCode.main(encrytext + "\r\n", true);
                //System.out.println("加密二维码已经生成！");
                System.out.println("Encryted QR code is generated！");
                System.out.println("-----------------------------------------------------------------------------");
                return encrytext;
            }
        }
        catch (FileNotFoundException e) {
            //System.out.println("找不到文件！请核对文件路径或查看文件是否存在.");
            System.out.println("Can not find the file！Please Check the file directory.");
        }
        catch(NullPointerException e){
            System.out.println("NullPointerException!");
        }
        return null;
     }
    /**
     * 随机生成由x个3位数组成的列序号.
     * @param str input original string
     * @param num numbers of 3-digits number
     * @return String 序列号
     */
    /**
     * Randomly generate x three-digit serial numbers.
     * @param str input original string
     * @param num numbers of 3-digits number
     * @return String serial number
     */
     public String generateStr(String str,int num){
        Random r =new Random();
        int value=r.nextInt(900)+100;
        str =str+""+value;
        for(int i=0;i<num-1;i++){
            Random r1 =new Random();
            value=r1.nextInt(900)+100;
            str= str+"-"+value;
        }
        return str;
     }
    public void main()throws Exception{
        crypt();
    }
}
