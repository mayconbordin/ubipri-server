package unit.util;

import org.junit.Test;
import play.Logger;
import utils.HashUtil;

import static org.junit.Assert.*;

/**
 * Created by mayconbordin on 25/09/15.
 */
public class HashUtilTest {

    @Test
    public void testcreatePassword() {
        String plain = "test";
        String password = HashUtil.createPassword(plain);
        System.out.println(plain + " = " + password);
        assertTrue(HashUtil.checkPassword(plain, password));

        plain = "12345";
        password = HashUtil.createPassword(plain);
        System.out.println(plain + " = " + password);
        assertTrue(HashUtil.checkPassword(plain, password));
    }
}
