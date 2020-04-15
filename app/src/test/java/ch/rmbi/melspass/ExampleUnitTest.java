package ch.rmbi.melspass;

import org.junit.Test;

import ch.rmbi.melspass.utils.PasswordGenerator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void password_generator_isCorrect()
    {
        int type = PasswordGenerator.USE_UPPERCASE | PasswordGenerator.USE_LOWERCASE | PasswordGenerator.USE_NUMBER | PasswordGenerator.USE_SEPCIALCHAR;
        int length = 6;
        assertNotNull(PasswordGenerator.getInstance().getRandomPassword(length ,type));
        assertEquals(PasswordGenerator.getInstance().getRandomPassword(length,type).length,length);
        assertNotEquals(String.valueOf(PasswordGenerator.getInstance().getRandomPassword(length,type)),String.valueOf(PasswordGenerator.getInstance().getRandomPassword(length,type)));

        //Length < 6 return null
        assertNull(PasswordGenerator.getInstance().getRandomPassword(4 ,type));
    }
}