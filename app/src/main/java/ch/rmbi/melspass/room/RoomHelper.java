package ch.rmbi.melspass.room;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import ch.rmbi.melspass.utils.FileUtils;

public class RoomHelper {


    public void BackupObjects(OutputStream out, Context context)  {
        LocalRoomDatabase db = LocalRoomDatabase.getInstance(context);
        List<GroupWithPass> objs =  db.groupWithPassDao().getGroupsWithPass();


        try {
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            for(int i=0;i<objs.size();i++)
            {
                objOut.writeObject(objs.get(i));
            }
            objOut.writeObject(null); //fix the end
        }catch (IOException ioe){
            ioe.printStackTrace();
        }


    }

    public void RestoreObjects(InputStream in, Context context ){
        RestoreObjects(in,context, true);
    }
    private void RestoreObjects(InputStream in, Context context,boolean withCopy )
    {
        ByteArrayOutputStream out = null ;
        try {
            ObjectInputStream objIn = new ObjectInputStream(in);
            LocalRoomDatabase db = LocalRoomDatabase.getInstance(context);

            if (withCopy) {
                out = new ByteArrayOutputStream();
                BackupObjects(out, context);
            }

            db.groupDao().deleteAll();
            GroupWithPass gwp = null;
            gwp = (GroupWithPass) objIn.readObject();
            while (gwp != null) {
                db.groupDao().insert(gwp.group);
                for (int i = 0; i < gwp.passList.size(); i++) {
                    gwp.passList.get(i).setGroup_id(gwp.group.getId());
                    db.passDao().insert(gwp.passList.get(i));
                }
                gwp = (GroupWithPass) objIn.readObject();
                objIn.available();
            }

        } catch (IOException | ClassNotFoundException e) {
            if (withCopy){
                ByteArrayInputStream inBackup = new ByteArrayInputStream(out.toByteArray());
                RestoreObjects(inBackup,context,false);
            }
            e.printStackTrace();
        }

    }



}
