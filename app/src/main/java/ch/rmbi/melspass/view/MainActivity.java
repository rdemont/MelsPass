package ch.rmbi.melspass.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Group;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.room.ViewModel;


public class MainActivity extends AppCompatActivity  {

    MenuItem menuSearch;
    MenuItem menuAddPass;
    MenuItem menuAddGroup;
    MenuItem menuEditPass;

    private boolean isLargeScreen = false ;
    private ViewModel groupViewModel ;
    //private List<GroupWithPass> listGroupWithPass ;
    private int groupPosition = 0;
    private int passPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLargeScreen = findViewById(R.id.frame_layout_detail) != null;

        groupViewModel = new ViewModelProvider(this).get(ViewModel .class);
        //listGroupWithPass = groupViewModel.getGroupsWithPass().getValue();
        show(groupViewModel.getGroupsWithPass());
    }

    public boolean getIsLargeScreen()
    {
        return isLargeScreen ;
    }

    public void show()
    {
        show(groupViewModel.getGroupsWithPass());
    }

    public void show(LiveData<List<GroupWithPass>> groups)
    {
        GroupListFragment groupListFragment = new GroupListFragment();
        groupListFragment.setGroups(groups);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();

        if (isLargeScreen) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, welcomeFragment).commit();
        }
    }


    public void show(Group group,boolean readWrite,int pos)
    {
        menuSearch.setVisible(true);
        menuAddPass.setVisible(false);
        menuAddGroup.setVisible(false);
        menuEditPass.setVisible(!readWrite);

        groupPosition = pos;
        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
        if (pos < 0)
        {
            groupDetailFragment.updateGroup(null, groupViewModel.getGroupsWithPass(), groupPosition);
        }else {
            groupDetailFragment.updateGroup(groupViewModel.getGroupsWithPass().getValue().get(groupPosition).group, groupViewModel.getGroupsWithPass(), groupPosition);
        }
        groupDetailFragment.setReadWrite(readWrite);
        if(isLargeScreen)
        {
            if (fragDetail.getClass() == GroupListFragment.class)
            {
                GroupListFragment groupListFragment = new GroupListFragment();
                groupListFragment.setGroups(groupViewModel.getGroupsWithPass());


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupListFragment).commit();

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, groupDetailFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, groupDetailFragment).commit();
        }
    }


    public void show(Pass pass, boolean readWrite,int pos)
    {
        menuSearch.setVisible(true);
        menuAddPass.setVisible(false);
        menuAddGroup.setVisible(false);
        menuEditPass.setVisible(!readWrite);

        passPosition = pos;

        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        PassDetailFragment passDetailFragment = new PassDetailFragment();
        if (pos < 0) {
            passDetailFragment.updatePass(null, null, groupPosition);
        }else {
            passDetailFragment.updatePass(groupViewModel.getGroupsWithPass().getValue().get(groupPosition).passList.get(passPosition), groupViewModel.getGroupsWithPass().getValue().get(groupPosition), passPosition);
        }
        passDetailFragment.setReadWrite(readWrite);
        if(isLargeScreen)
        {
            if (fragDetail.getClass() == PassListFragment.class)
            {
                PassListFragment passListFragment = new PassListFragment();
                passListFragment.updatePassGroup(((PassListFragment)fragDetail).groupWithPass,groupViewModel.getGroupsWithPass());


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passListFragment).commit();

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passDetailFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passDetailFragment).commit();
        }

    }

    public void show(GroupWithPass groupWithPass) {
        show(groupWithPass,groupPosition);
    }

    public void show(GroupWithPass groupWithPass,int position)
    {
        menuSearch.setVisible(true);
        menuAddPass.setVisible(true);
        menuAddGroup.setVisible(true);
        menuEditPass.setVisible(false);


        groupPosition = position ;

        Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);

        PassListFragment passListFragment = new PassListFragment();
        passListFragment.updatePassGroup(groupWithPass, groupViewModel.getGroupsWithPass());
        if(isLargeScreen)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_detail, passListFragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main, passListFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menuSearch = menu.findItem (R.id.app_bar_search);
        menuAddPass = menu.findItem (R.id.action_add_pass);
        menuAddGroup = menu.findItem (R.id.action_add_group);
        menuEditPass = menu.findItem (R.id.action_edit_pass);
        menuEditPass.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_add_group:
                show((Group)null,true,-1);
                return true;
            case R.id.action_add_pass:
                show((Pass)null,true,-1);
                return true;
            case R.id.app_bar_search:
                return true;
            case R.id.action_edit_pass:
                Fragment fragMain = getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
                Fragment fragDetail = getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
                Pass pass = null ;
                if(fragDetail!= null && fragDetail.isVisible())
                {
                    if(fragDetail.getClass() == PassDetailFragment.class)
                    {
                        pass = ((PassDetailFragment)fragDetail).pass;
                    }
                }else{
                    if(fragMain.getClass() == PassDetailFragment.class)
                    {
                        pass = ((PassDetailFragment)fragMain).pass;
                    }
                }
                if (pass != null)
                {
                    show(pass,true,passPosition);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
