package ch.rmbi.melspass.utils;

import java.lang.reflect.Field;

public class Tools {
    private static Tools instance = null;

    public static Tools getInstance() {
        return instance;
    }


    //Tools.getRessourceId(src,R.drawable.class)
    public static int getRessourceId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }




}
