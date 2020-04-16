package ch.rmbi.melspass.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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

    MenuItem menuSearch;
    MenuItem menuAddPass;
    MenuItem menuAddGroup;


    private boolean isLargeScreen = false ;
    private ViewModel groupViewModel ;

    private int groupPosition = 0;
    private int passPosition = 0;

    public List<GroupWithPass> getGroupsWithPass() {
        return groupsWithPass;
    }

    private List<GroupWithPass> groupsWithPass;

    public ViewModel getGroupViewModel() {
        return groupViewModel;
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

        groupViewModel = new ViewModelProvider(this).get(ViewModel .class);

        groupViewModel.getGroupsWithPass().observe(this, new Observer<List<GroupWithPass>>() {
            @Override
            public void onChanged(List<GroupWithPass> grpWithPass) {
                boolean isFirst = groupsWithPass== null ;
                groupsWithPass = grpWithPass;
                if(isFirst){
                    show();
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
        menuSearch.setVisible(true);
        if (readWrite) {
            menuAddPass.setVisible(false);
            menuAddGroup.setVisible(false);
        }else {
            menuAddPass.setVisible(true);
            menuAddGroup.setVisible(true);
        }


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
        menuSearch.setVisible(true);
        if (readWrite) {
            menuAddPass.setVisible(false);
            menuAddGroup.setVisible(false);
        }else {
            menuAddPass.setVisible(true);
            menuAddGroup.setVisible(true);
        }

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
        menuSearch.setVisible(true);
        menuAddPass.setVisible(true);
        menuAddGroup.setVisible(true);

        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        PassListFragment passListFragment = new PassListFragment();


        if(isLargeScreen)
        {
            if (fragMain.getClass() != GroupListFragment.class)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, new GroupListFragment()).commit();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passListFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passListFragment).commit();
        }


    }
    public void showPassList(int grpPos)
    {
        groupPosition = grpPos ;
        showPassList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuSearch = menu.findItem (R.id.app_bar_search);
        menuAddPass = menu.findItem (R.id.action_add_pass);
        menuAddGroup = menu.findItem (R.id.action_add_group);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_add_group:
                showGroup(true,true);
                return true;
            case R.id.action_add_pass:
                showPass(true,true);
                return true;
            case R.id.app_bar_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
