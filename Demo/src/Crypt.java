import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class Crypt {
    DESUtils DES = new DESUtils();
    QRCodeUtil QRCode = new QRCodeUtil();
    Ceasar ceasar = new Ceasar();
    Object[] list =null;
    /**
     * 此class负责进行二维码加密及输出,
     * 2019-8-28 Albert
     */
    public Crypt() {
            System.out.println("是否需要RSA加密DES密匙？ 若需要请输入Yes,若不需要请输入任意字符跳过...");
            Scanner scan = new Scanner(System.in);
            if(scan.nextLine().equalsIgnoreCase("YES")){
                System.out.println("使用RSA加密DES密匙.");
                DES.main(true);
            }
            else{
                System.out.println("不使用RSA加密DES密匙.");
                DES.main(false);
            }

    }

    public String  crypt() throws Exception{
        try {
            System.out.println("----------------------------正在对明文进行重排列------------------------------");
            //String filename= QRCode.main("My name is Zyc", true);//生成明文二维码
            //System.out.println("明文二维码已经生成！");
            //String text = QRCode.decode(filename);//提取明文
            /**
             * 请在此处修改您想要生成二维码的信息.
             */
            String text = "生产编号:297182944981231691623293712765498712321";
            list = ceasar.main(text);//对明文进行重排列
            StringBuffer cipher = (StringBuffer) list[0];//密文
            int swift = (int) list[1];//偏移量
            System.out.println("---------------------------------正在生成二维码-------------------------------");
            String encryname = DES.get_encryname_key_pair(cipher.toString()+swift)[0];
            String key = DES.get_encryname_key_pair(cipher.toString()+swift)[1];
            //得到加密明文
            System.out.println("加密明文:"+encryname);
            System.out.println("DES密匙:"+key);
            QRCode.main(encryname+"\r\n"+key, true);//生成加密二维码
            System.out.println("加密二维码已经生成！");
            System.out.println("-----------------------------------------------------------------------------");
            return encryname;
        }
        catch (FileNotFoundException e) {
            System.out.println("找不到文件！请核对文件路径或查看文件是否存在.");
        }
        catch(NullPointerException e){
            System.out.println("NullPointerException!");
        }

        return null;
     }
    public void main()throws Exception{
        crypt();
    }
}