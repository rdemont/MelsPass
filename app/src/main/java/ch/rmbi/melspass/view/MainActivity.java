package ch.rmbi.melspass.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.Pass;
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
    public List<Pass> getSearchPassList() {
        return searchPassList;
    }
    private List<GroupWithPass> groupsWithPass;
    private List<Pass> searchPassList ;


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
                    if (searchPassList == null) {
                        switch (waitingNext) {
                            case NEXT_GROUP_LIST:
                                showGroupList();
                                break;
                            case NEXT_PASS_LIST:
                                showPassList();
                                break;
                            default:
                                showGroupList();
                        }
                        isWaitingLiveDate = false;
                        waitingNext = NEXT_DEFAULT;
                    }
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

    public void cancelSearch(){
        searchPassList = null ;
        //groupPosition = 0;
        passPosition = 0;
    }
    /*
        public void show()
        {

            showGroupList();

            cancelSearch();
            GroupListFragment groupListFragment = new GroupListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();

            if (isLargeScreen) {
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
            }


        }
     */
    public void showSearch(String search)
    {
        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        viewModel.getSearchPass(search).observe(this, new Observer<List<Pass>>() {
            @Override
            public void onChanged(List<Pass> pass) {
                searchPassList = pass;
                showSearchResultList();
            }
        });
    }

    public void showSearchPass(int position)
    {
        passPosition = position;

        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        SearchResultFragment resultFrag = new SearchResultFragment();
        if(isLargeScreen)
        {
            PassDetailFragment passDetailFragment = new PassDetailFragment();
            passDetailFragment.setisNew(false);
            passDetailFragment.setReadWrite(false);

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,  resultFrag).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passDetailFragment).commit();

        }else{
            showPass(false,false,position);
        }

    }

    public void showSearchResultList(){


        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        SearchResultFragment resultFrag = new SearchResultFragment();

        if(isLargeScreen)
        {
            PassDetailFragment passDetailFragment = new PassDetailFragment();
            passDetailFragment.setisNew(false);
            passDetailFragment.setReadWrite(false);
            passPosition = 0 ;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,  resultFrag).commit();
            if ((searchPassList != null) && (searchPassList.size() > 0)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passDetailFragment).commit();
            }

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, resultFrag).commit();
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
        cancelSearch();
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
            if (searchPassList != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new SearchResultFragment()).commit();
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new PassListFragment()).commit();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passDetailFragment).commit();

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passDetailFragment).commit();
        }

    }


    public void showGroupList()
    {
        cancelSearch();


        if (isLargeScreen) {
            showPassList();
        }else {
            GroupListFragment groupListFragment = new GroupListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();
        }
    }

    public void showPassList()
    {

        cancelSearch();
        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        PassListFragment passListFragment = new PassListFragment();


        if(isLargeScreen)
        {
            if (groupsWithPass.size() == 0 ){
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
            }else {
                if (groupsWithPass.size() < groupPosition) {
                    groupPosition = 0;
                }
            }
            if (groupsWithPass.get(groupPosition).passList.size() <= 0 ){
                WelcomeFragment welcomeFragment = new WelcomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
            }else {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passListFragment).commit();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new GroupListFragment()).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passListFragment).commit();
        }


    }


    public void showPassList(int grpPos)
    {
        groupPosition = grpPos ;
        showPassList();

    }

}
