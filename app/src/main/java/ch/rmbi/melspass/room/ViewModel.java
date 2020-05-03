package ch.rmbi.melspass.room;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;



    public ViewModel(Application application) {
        super(application);
        repository = new Repository(application);
    }

    public int getGroupMaxOrder(){
        return repository.getGroupMaxOrder();
    }

    public int getPassMaxOrder(){
        return repository.getPassMaxOrder();
    }

    public List<Group> getGroups() {
        return repository.getGroups();
    }



    public LiveData<List<GroupWithPass>> getGroupsWithPass() {
        return repository.getGroupsWithPass();
    }

    public List<PassWithGroup> getPassWithGroup() {
        return repository.getPassWithGroup();
    }

    public List<Pass> getPassList() {
        return repository.getPassList();
    }


    public LiveData<List<Pass>> getSearchPass(String search) {
        return repository.getSearchPass(search);
    }


    public Setup getSetup()
    {
        return repository.getSetup();
    }

    public void insert(Pass pass) {
        repository.insert(pass);
    }
    public void update(Pass... pass) {
        repository.update(pass);
    }
    public void delete(Pass... pass) {
        repository.delete(pass);
    }

    public void insert(Group group) {
        repository.insert(group);
    }
    public void update(Group... group) {
        repository.update(group);
    }
    public void delete(Group... group) {
        repository.delete(group);
    }

    public void insert(Setup setup) {
        repository.insert(setup);
    }
    public void update(Setup... setup) {
        repository.update(setup);
    }
    public void delete(Setup... setup) {
        repository.delete(setup);
    }


    public void BackupDB(String outFileName){repository.BackupDB(outFileName);}
    public void RestoreDB(String inFileName){repository.RestoreDB(inFileName);}

}
