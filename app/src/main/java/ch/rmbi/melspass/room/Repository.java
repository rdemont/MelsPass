package ch.rmbi.melspass.room;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.List;

public class Repository {

    private LocalRoomDatabase db ;
    public Repository(Application application)
    {
        db = LocalRoomDatabase.getInstance(application);
    }

    public LiveData<List<GroupWithPass>> getGroupsWithPass() {
        return  db.groupWithPassDao().getLiveGroupsWithPass();
    }

    public List<PassWithGroup> getPassWithGroup() {
        return db.passWithGroupDao().getPassWithGroup();
    }

    public List<Group> getGroups() {
        return db.groupDao().getGroups();
    }

    public List<Pass> getPass(Group group)
    {
        return db.groupDao().findPassForGroup(group.getId());
    }

    public List<Pass> getPassList() { return db.passDao().getAllPass() ;}

    public LiveData<List<Pass>> getSearchPass(String search){
        String str = "%"+search+"%";
        return db.passDao().getSearchPass(str);
    }


    public int getGroupMaxOrder(){
        return  db.groupDao().getGroupMaxOrder();
    }

    public int getPassMaxOrder(){
        return db.passDao().getPassMaxOrder();
    }




    public void insert(Group group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.groupDao().insert(group);
        });
    }

    public void insert(Pass pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.passDao().insert(pass);
        });
    }


    public void update(Pass... pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.passDao().update(pass);
        });
    }

    public void delete(Pass... pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.passDao().delete(pass);
        });
    }


    public void update(Group... group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.groupDao().update(group);
        });
    }

    public void delete(Group... group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.groupDao().delete(group);
        });
    }


    public Setup getSetup(){
        return db.setupDao().getSetup();
    }



    public void insert(Setup setup) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.setupDao().insert(setup);
        });
    }


    public void update(Setup... setup) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.setupDao().update(setup);
        });
    }

    public void delete(Setup... setup) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            db.setupDao().delete(setup);
        });
    }




    public void BackupDB(String outFileName){db.BackupDB(outFileName);}
    public void RestoreDB(String inFileName){db.RestoreDB(inFileName);}
}
