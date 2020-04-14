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
    private GroupDao groupDao ;
    private PassDao passDao ;
    private GroupWithPassDao groupWithPassDao;

    private LiveData<List<GroupWithPass>> groupsWithPass;
    private List<Group> groups;

    private MediatorLiveData<List<GroupWithPass>> mSectionLive = new MediatorLiveData<>();

    public Repository(Application application)
    {
        LocalRoomDatabase db = LocalRoomDatabase.getInstance(application);
        groupDao = db.groupDao();
        passDao = db.passDao();
        groupWithPassDao = db.groupWithPassDao();

        groupsWithPass = db.groupWithPassDao().getLiveGroupsWithPass();

    }

    public LiveData<List<GroupWithPass>> getGroupsWithPass() {
        return groupsWithPass;
    }

    public List<Group> getGroups() {
        return groupDao.getGroups();
    }

    public List<Pass> getPass(Group group)
    {
        return groupDao.findPassForGroup(group.getId());
    }


    public void insert(Group group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.insert(group);
        });
    }

    public void insert(Pass pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            passDao.insert(pass);
        });
    }


    public void update(Pass... pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            passDao.update(pass);
        });
    }

    public void delete(Pass... pass) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            passDao.delete(pass);
        });
    }


    public void update(Group... group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.update(group);
        });
    }

    public void delete(Group... group) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            groupDao.delete(group);
        });
    }
}
