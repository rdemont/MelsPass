package ch.rmbi.melspass.utils;

import java.util.HashMap;

public class Session {

    public static final String KEY_GROUP_WITH_PASS = "KEY_GROUP_WITH_PASS";
    //public static final String KEY_PASS_OBJECT = "KEY_PASS_OBJECT";
    //public static final String KEY_POSITION = "KEY_POSITION";

    private static volatile Session instance ;
    private HashMap objects ;


    private Session()
    {
        objects = new HashMap();

    }


    public void put (Object key, Object value)
    {
        objects.put(key,value);
    }

    public Object get(Object key)
    {
        return objects.get(key);
    }

    public static Session getInstance(){
        if (instance == null) {
            synchronized (Session.class) {
                if (instance == null) {
                    instance = new Session();
                }
            }
        }
        return instance;
    }
}
