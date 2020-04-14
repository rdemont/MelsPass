package ch.rmbi.melspass.room;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;
//    private List<Group> groups;
    private LiveData<List<GroupWithPass>> groupsWithPath;

    public ViewModel(Application application) {
        super(application);
        repository = new Repository(application);
//        groups = repository.getAllGroups();
        groupsWithPath = repository.getGroupsWithPass();
    }

  /*
    public List<Group> getAllGroups() {
        return groups;
    }


   */
    public LiveData<List<GroupWithPass>> getGroupsWithPass() {
        return groupsWithPath;
    }

    /*
    public List<Pass> getPass(Group group)
    {
        return repository.getPass(group);
    }
*/
    void insert(Group group) {
        repository.insert(group);
    }
}
