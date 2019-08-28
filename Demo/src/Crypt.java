import java.io.FileInputStream;
import java.io.ObjectInputStream;

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
        DES.main();
    }

    public String  crypt() {
        try {
            System.out.println("----------------------------正在对明文进行重排列------------------------------");
            //String filename= QRCode.main("My name is Zyc", true);//生成明文二维码
            //System.out.println("明文二维码已经生成！");
            //String text = QRCode.decode(filename);//提取明文
            /**
             * 请在此处修改您想要生成二维码的信息.
             */
            String text = "生产编号99497638498178497789273148765438412321";
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
        catch (Exception e) {
            System.out.println("Error！请核对文件路径或查看文件是否存在.");
        }
        return null;
     }
    public void main(){
        crypt();
    }
}