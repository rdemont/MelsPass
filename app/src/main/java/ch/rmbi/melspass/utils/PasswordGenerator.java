package ch.rmbi.melspass.utils;

import java.util.Random;
import java.util.regex.Pattern;

public class PasswordGenerator {

    private static PasswordGenerator instance = null;

    final String UPPER_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String LOWER_STR = "abcdefghijklmnopqrstuvwxyz";
    final String NUMBER_STR = "0123456789";
    final String SPECIAL_STR = "!@#$%^&*()=_<>?+/{}~|";


    final Pattern pUpper = Pattern.compile(".*["+UPPER_STR+"].*");
    final Pattern pLower = Pattern.compile(".*["+LOWER_STR+"].*");
    final Pattern pNumber = Pattern.compile(".*["+NUMBER_STR+"].*");
    final Pattern pSpecial = Pattern.compile(".*["+SPECIAL_STR+"].*");

    public final static int USE_UPPERCASE = 1;
    public final static int USE_LOWERCASE = 2;
    public final static int USE_NUMBER = 4;
    public final static int USE_SEPCIALCHAR = 8;

    private PasswordGenerator(){;}

    public static PasswordGenerator getInstance(){
        if (instance == null)
            instance = new PasswordGenerator();
        return instance ;
    }


    public char[] getRandomPassword(int length, int type){

        if (length < 6 )
        {
            return null ;
        }
        String valideChar = "";


        if ((type & USE_UPPERCASE) == USE_UPPERCASE )
        {
            valideChar += UPPER_STR;

        }
        if ((type & USE_LOWERCASE) == USE_LOWERCASE )
        {
            valideChar += LOWER_STR;

        }
        if ((type & USE_NUMBER) == USE_NUMBER )
        {
            valideChar += NUMBER_STR;

        }
        if ((type & USE_SEPCIALCHAR) == USE_SEPCIALCHAR )
        {
            valideChar += SPECIAL_STR;

        }
        char[] useChar = valideChar.toCharArray();

        final Random random=new Random();
        int loop = random.nextInt(10000);
        for(int i = 0;i<loop;i++)
        {
            int start = random.nextInt(useChar.length);
            int end = random.nextInt(useChar.length);
            char c = useChar[end];
            useChar[end] = useChar[start];
            useChar[start] = c;
        }

        char[] result = new char[length] ;

        for(int i=0;i<length;++i)
        {
            result[i] = useChar[random.nextInt(useChar.length)];
        }




        if ((type & USE_UPPERCASE) == USE_UPPERCASE )
        {
            if (! String.valueOf(result).matches(pUpper.pattern()) ){
                return getRandomPassword(length,type);
            }
        }
        if ((type & USE_LOWERCASE) == USE_LOWERCASE )
        {
            if (! String.valueOf(result).matches(pLower.pattern()) ){
                return getRandomPassword(length,type);
            }
        }
        if ((type & USE_NUMBER) == USE_NUMBER )
        {
            if (! String.valueOf(result).matches(pNumber.pattern()) ){
                return getRandomPassword(length,type);
            }
        }
        if ((type & USE_SEPCIALCHAR) == USE_SEPCIALCHAR )
        {
            if (! String.valueOf(result).matches(pSpecial.pattern()) ){
                return getRandomPassword(length,type);
            }
        }

        return result;
    }
}
