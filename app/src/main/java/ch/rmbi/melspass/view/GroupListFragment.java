package ch.rmbi.melspass.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.GroupListAdapter;
import ch.rmbi.melspass.utils.ItemClickSupport;


public class GroupListFragment extends TemplateFragment {

    RecyclerView rvGroups;
    //private LiveData<List<GroupWithPass>> groupsWithPass;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_group_list;
    }

    @Override
    protected String getFragmentTitle() {
        return getResources().getString(R.string.list_group_fragment_title);
    }

    @Nullable
    @Override
    public void onCreateAppView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater,container,savedInstanceState);


        View rootView = getRootView();

        rvGroups = (RecyclerView) rootView.findViewById(R.id.rvGroups);
        final GroupListAdapter groupListAdapter = new GroupListAdapter(getContext());
        groupListAdapter.setParentActivity(getActivity());
        rvGroups.setAdapter(groupListAdapter);
        rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));



        groupListAdapter.setGroupsWithPass(((MainActivity)getActivity()).getGroupsWithPass());

        this.configureOnClickRecyclerView();



        //ImageButton bAdd = rootView.findViewById(R.id.bAdd);
        getButtonAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(true,true);
            }
        });





    }

    @Override
    protected int getFragmentButtonVisible() {
        if (((MainActivity)getActivity()).getIsLargeScreen()){
            return SHOW_BUTTON_ADD;
        }
        return SHOW_BUTTON_ADD | SHOW_BUTTON_SEARCH;
    }

    @Override
    protected void onBackPressed() {
        ((MainActivity)getActivity()).finish();
        System.exit(0);

    }


    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(rvGroups, R.layout.group_list_layout)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((MainActivity)getActivity()).showPassList(position);
                    }
                });
    }
}
