public class Crypt {
    DESUtils Des = new DESUtils();
    QRCodeUtil QRCode = new QRCodeUtil();

    public Crypt() {
    }

    public void crypt() {
        try {
            QRCode.main("HelloWorld!", "明文", true);//生成明文二维码
            System.out.println("明文二维码已经生成！");
            String text = QRCode.decode("明文");//提取明文
            System.out.println("-----------------------------------------------------------------------------");
            String encoded_text = Des.get_encryname(text);//得到加密明文
            System.out.println("加密明文:"+encoded_text);
            QRCode.main("内容:"+encoded_text+"\n使用RSA公匙加密后的DES密匙："+Des.getEncryptedDESkeyByRSAPubkey()+"\nRSA公钥:"+Des.getRSAPubkey(),"加密", true);//生成加密二维码
            System.out.println("加密二维码已经生成！");
            System.out.println("-----------------------------------------------------------------------------");
        }
        catch (Exception e) {
                System.out.println("Error！");
        }
     }
    public static void main(String[] args){
        Crypt Albert = new Crypt();
        Albert.crypt();
    }
}