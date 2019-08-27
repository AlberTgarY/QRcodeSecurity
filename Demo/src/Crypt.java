import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Crypt {
    DESUtils DES = new DESUtils();
    QRCodeUtil QRCode = new QRCodeUtil();

    public Crypt() {
        DES.main();
    }

    public String  crypt() {
        try {
            System.out.println("---------------------------正在生成二维码-------------------------------------");
            //String filename= QRCode.main("Hd!", true);//生成明文二维码
            //System.out.println("明文二维码已经生成！");
            //String text = QRCode.decode(filename);//提取明文
            /**
             * 请在此处修改您想要生成二维码的信息.
             */
            String text = "货真价实zyc";
            System.out.println("-----------------------------------------------------------------------------");
            String encryname = DES.get_encryname_key_pair(text)[0];
            String key = DES.get_encryname_key_pair(text)[1];
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