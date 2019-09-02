import org.junit.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.Assert.*;

public class RSAUtilsTest {

    @Test
    public void  getRSAKey() throws Exception{
        //TODO get 100 keypairs in a row
        for(int i =0;i<100;i++){
            System.out.println(RSAUtils.getRSAKey());
            i+=1;
        }
    }
}