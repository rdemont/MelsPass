package ch.rmbi.melspass.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.ViewModel;


public class MainActivity extends AppCompatActivity  {
    public final static int NEXT_DEFAULT = 0;
    public final static int NEXT_GROUP_LIST = 1;
    public final static int NEXT_PASS_LIST = 2;


    public void setWaitingLiveDate(boolean waitingLiveDate) {
        isWaitingLiveDate = waitingLiveDate;
    }

    boolean isWaitingLiveDate = true ;
    int waitingNext ;

    public int getWaitingNext() {
        return waitingNext;
    }

    public void setWaitingNext(int waitingNext) {
        this.waitingNext = waitingNext;
    }

    private boolean isLargeScreen = false ;
    private ViewModel viewModel;

    private int groupPosition = 0;
    private int passPosition = 0;

    public List<GroupWithPass> getGroupsWithPass() {
        return groupsWithPass;
    }

    private List<GroupWithPass> groupsWithPass;

    public ViewModel getViewModel() {
        return viewModel;
    }



    public int getGroupPosition() {
        return groupPosition;
    }

    public int getPassPosition() {
        return passPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLargeScreen = findViewById(R.id.frame_layout_detail) != null;

        viewModel = new ViewModelProvider(this).get(ViewModel .class);

        viewModel.getGroupsWithPass().observe(this, new Observer<List<GroupWithPass>>() {
            @Override
            public void onChanged(List<GroupWithPass> grpWithPass) {
                if (groupsWithPass == null){
                    isWaitingLiveDate = true;
                }

                groupsWithPass = grpWithPass;
                if(isWaitingLiveDate){
                    switch (waitingNext) {
                        case NEXT_GROUP_LIST :
                            showGroupList();
                            break;
                        case NEXT_PASS_LIST :
                            showPassList();
                            break;
                        default:
                            show();
                    }
                    isWaitingLiveDate = false ;
                    waitingNext = NEXT_DEFAULT;
                }

            }
        });


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public boolean getIsLargeScreen()
    {
        return isLargeScreen ;
    }

    public void show()
    {
        GroupListFragment groupListFragment = new GroupListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();

        if (isLargeScreen) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
        }
    }

    public void showGroup(boolean readWrite)
    {
        showGroup(readWrite,false);
    }

    public void showGroup(boolean readWrite,boolean isNew,int pos)
    {
        groupPosition = pos ;
        showGroup(readWrite,isNew);
    }

    public void showGroup(boolean readWrite,boolean isNew)
    {

        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
        groupDetailFragment.setisNew(isNew);
        groupDetailFragment.setReadWrite(readWrite);

        if(isLargeScreen)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,  new GroupListFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, groupDetailFragment).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupDetailFragment).commit();
        }
    }

    public void showPass(boolean readWrite)
    {
        showPass(readWrite,false);
    }

    public void showPass(boolean readWrite,boolean isNew,int pos)
    {
        passPosition = pos ;
        showPass(readWrite,isNew);
    }

    public void showPass(boolean readWrite,boolean isNew)
    {


        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        PassDetailFragment passDetailFragment = new PassDetailFragment();

        passDetailFragment.setisNew(isNew);
        passDetailFragment.setReadWrite(readWrite);
        if(isLargeScreen)
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,  new PassListFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passDetailFragment).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passDetailFragment).commit();
        }

    }


    public void showGroupList()
    {
        GroupListFragment groupListFragment = new GroupListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();

        if (isLargeScreen) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
        }
    }

    public void showPassList()
    {


        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        PassListFragment passListFragment = new PassListFragment();


        if(isLargeScreen)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new GroupListFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passListFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passListFragment).commit();
        }


    }

    public void showSearch()
    {
        ;
    }

    public void showPassList(int grpPos)
    {
        groupPosition = grpPos ;
        showPassList();

    }

}
