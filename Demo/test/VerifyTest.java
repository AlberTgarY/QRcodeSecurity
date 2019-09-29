import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class VerifyTest {
    @Test
    public  void getQRcode1()throws Exception{
        Verify veri= new Verify();
        //TODO get 1000 QRcode in a row (仅供测试，需要注释部分代码).
        veri.getQRcode(1);

    }

}