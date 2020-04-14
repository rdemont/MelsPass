package ch.rmbi.melspass.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.PassListAdapter;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.ViewModel;
import ch.rmbi.melspass.utils.ItemClickSupport;
import ch.rmbi.melspass.utils.Session;

public class PassListFragment extends Fragment {

    RecyclerView rvPassList;
    private ViewModel passViewModel ;
    GroupWithPass groupWithPass;
    LiveData<List<GroupWithPass>> groups;
    private PassListFragment.OnItemClickedListener mCallback;

    public interface OnItemClickedListener {
        public void OnItemClicked(View view, int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pass_list,container,false);


        rvPassList = (RecyclerView) rootView.findViewById(R.id.rvPassList);
        final PassListAdapter passListAdapter = new PassListAdapter(rvPassList.getContext());
        rvPassList.setAdapter(passListAdapter);
        rvPassList.setLayoutManager(new LinearLayoutManager(rvPassList.getContext()));
        passListAdapter.setPass(groupWithPass.passList);

        passViewModel = new ViewModelProvider(this).get(ViewModel.class);

        passViewModel.getGroupsWithPass().observe(this, new Observer<List<GroupWithPass>>() {
            @Override
            public void onChanged(List<GroupWithPass> groupsWithPass) {
                //passListAdapter.setGroupsWithPass(groupsWithPass);
            }
        });


        ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        bBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(groups);
            }
        });


        this.configureOnClickRecyclerView();

        return rootView;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
 //       this.createCallbackToParentActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(rvPassList, R.layout.pass_list_layout)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        ((MainActivity)getActivity()).show(groupWithPass.passList.get(position),false,position);
                    }
                });
    }

    public void updatePassGroup(GroupWithPass grp,LiveData<List<GroupWithPass>> groupLst)
    {
        groupWithPass = grp;
        groups = groupLst;


    }

}
