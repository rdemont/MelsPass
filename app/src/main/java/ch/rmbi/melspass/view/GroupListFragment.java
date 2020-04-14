package ch.rmbi.melspass.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.GroupListAdapter;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.ViewModel;
import ch.rmbi.melspass.utils.ItemClickSupport;
import ch.rmbi.melspass.utils.Session;


public class GroupListFragment extends OwnFragment  {

    RecyclerView rvGroups;
    private LiveData<List<GroupWithPass>> groupsWithPass;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_list,container,false);

        rvGroups = (RecyclerView) rootView.findViewById(R.id.rvGroups);
        final GroupListAdapter groupListAdapter = new GroupListAdapter(getContext());
        groupListAdapter.setParentActivity(getActivity());
        rvGroups.setAdapter(groupListAdapter);
        rvGroups.setLayoutManager(new LinearLayoutManager(getContext()));

        groupsWithPass = ((MainActivity)getActivity()).getGroupViewModel().getGroupsWithPass();
        groupsWithPass.observe(this, new Observer<List<GroupWithPass>>() {
            @Override
            public void onChanged(List<GroupWithPass> groupsWithPass) {
                groupListAdapter.setGroupsWithPass(groupsWithPass);
            }
        });

        this.configureOnClickRecyclerView();


        return rootView;
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
