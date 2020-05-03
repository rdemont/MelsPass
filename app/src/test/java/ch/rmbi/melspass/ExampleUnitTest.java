package ch.rmbi.melspass;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;


import ch.rmbi.melspass.room.RoomHelper;
import ch.rmbi.melspass.utils.CryptoHelper;
import ch.rmbi.melspass.utils.FileUtils;
import ch.rmbi.melspass.utils.PasswordGenerator;

import static org.junit.Assert.*;
import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;


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

    @Test
    public void crypto_isCorrect() throws InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, UnsupportedEncodingException {
        String in = "blabla bla";
        String out ;
        out = CryptoHelper.getInstance().encrypt(in);
        assertNotEquals(in,out);

        assertEquals(CryptoHelper.getInstance().decrypt(out),in);

    }


}