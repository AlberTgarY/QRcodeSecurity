import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
public class Crypt {
    private DESUtils DES = new DESUtils();
    private RSAUtils RSA = new RSAUtils();
    private QRCodeUtil QRCode = new QRCodeUtil();
    private Ceasar ceasar = new Ceasar();
    /**
     * 此class负责进行二维码加密及输出,
     * 2019-8-28 Albert
     */
    public Crypt() {

        System.out.println("是否使用DES加密二维码并且使用RSA加密DES密匙？ 若需要请输入Yes,若仅使用DES或RSA加密二维码请输入任意字符跳过...");
        Scanner Scan = new Scanner(System.in);
        if(Scan.nextLine().equalsIgnoreCase("YES")){
            System.out.println("----->使用RSA加密DES密匙.");
            DES.main(true);
        }
        else{
            System.out.println("----->不使用RSA加密DES密匙.");
            DES.main(false);
        }
    }
    /**
     * 加密部分
     * @return String 加密后的text
     * @throws Exception
     */

    public String  crypt() throws Exception{
        try {
            System.out.println("是否使用DES加密二维码？若是请输入Yes,若使用RSA加密请输入任意字符跳过...");
            Scanner scan = new Scanner(System.in);
            if (scan.nextLine().equalsIgnoreCase("YES")) {

                System.out.println("-------------------------DES加密,正在对明文进行重排列--------------------------");
                /**
                 * 请在此处修改您想要生成二维码的信息.
                 */
                Object[] list;
                String text = "Albert HACKATHON:";
                /**
                 * comment text = generateStr(text, 9); if you dont want to generate random serial numbers.
                 */
                text = generateStr(text, 9);//generate random serial number
                list = ceasar.main(text);//对明文进行重排列
                StringBuffer cipher = (StringBuffer) list[0];//密文
                int swift = (int) list[1];//偏移量
                System.out.println("------------------------------正在生成二维码----------------------------------");
                String[] pair_list = DES.get_encryname_key_pair(cipher.toString() + swift);
                String encryname = pair_list[0];
                String key = pair_list[1];
                //得到加密明文
                System.out.println("加密明文:" + encryname);
                System.out.println("DES密匙:" + key);
                QRCode.main(encryname + "\r\n" + key, true);//生成加密二维码
                System.out.println("加密二维码已经生成！");
                System.out.println("-----------------------------------------------------------------------------");
                return encryname;
            } else {
                System.out.println("----------------------RSA加密,正在对明文进行重排列----------------------------");
                //String filename= QRCode.main("My name is Zyc", true);//生成明文二维码
                //System.out.println("明文二维码已经生成！");
                //String text = QRCode.decode(filename);//提取明文
                /**
                 * 请在此处修改您想要生成二维码的信息.
                 */
                Object[] list;
                String text = "Serialnumber:";
                text = generateStr(text, 9);
                list = ceasar.main(text);//对明文进行重排列
                StringBuffer cipher = (StringBuffer) list[0];//密文
                int swift = (int) list[1];//偏移量
                System.out.println("------------------------------正在生成二维码----------------------------------");
                String encrytext = RSA.main(cipher.toString() + swift);
                //得到加密明文
                System.out.println("加密明文:" + encrytext);
                QRCode.main(encrytext + "\r\n", true);//生成加密二维码
                System.out.println("加密二维码已经生成！");
                System.out.println("-----------------------------------------------------------------------------");
                return encrytext;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("找不到文件！请核对文件路径或查看文件是否存在.");
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