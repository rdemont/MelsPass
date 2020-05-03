package ch.rmbi.melspass.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {


    public static File createPublicFolder(Context context, String  directoryName){
        File myDirectory = new File(context.getExternalFilesDir(null),directoryName);
        if (!myDirectory.exists()) {
            myDirectory.mkdir();
        }
        return myDirectory ;
    }
}
