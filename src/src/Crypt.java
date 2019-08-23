import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Crypt {
    DESUtils DES = new DESUtils();
    RSAUtils RSA = DES.main();
    QRCodeUtil QRCode = new QRCodeUtil();

    public Crypt() {

    }

    public String  crypt() {
        try {
            System.out.println("-----------------------------------------------------------------------------");
            //String filename= QRCode.main("Hd!", true);//生成明文二维码
            //System.out.println("明文二维码已经生成！");
            //String text = QRCode.decode(filename);//提取明文
            String text = "Hello World!";
            System.out.println("-----------------------------------------------------------------------------");
            String encryname = DES.get_encryname_key_pair(text)[0];
            String key = DES.get_encryname_key_pair(text)[1];
            //得到加密明文
            System.out.println("加密明文:"+encryname);
            System.out.println("DES密匙:"+key);
            QRCode.main("内容:"+encryname+"\n加密DES密匙:"+key, true);//生成加密二维码
            System.out.println("加密二维码已经生成！");
            System.out.println("-----------------------------------------------------------------------------");
            return encryname;
        }
        catch (Exception e) {
            System.out.println("Error！请核对文件路径或查看文件是否存在.");
        }
        return null;
     }
    public Object[] main(){
        String text = crypt();
        Object[] list ={DES,RSA,text,QRCode};
        return list;
    }
}