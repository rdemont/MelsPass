package ch.rmbi.melspass.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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


public class PassListFragment extends TemplateFragment {

    RecyclerView rvPassList;
    private ViewModel passViewModel ;
    GroupWithPass groupWithPass;

    private PassListFragment.OnItemClickedListener mCallback;

    public interface OnItemClickedListener {
        public void OnItemClicked(View view, int position);
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_pass_list ;
    }

    @Override
    protected String getFragmentTitle() {
        return "List des Pasword du group xx";
    }


    @Override
    protected int getFragmentButtonVisible() {
        return SHOW_BUTTON_ADD | SHOW_BUTTON_SEARCH | SHOW_BUTTON_BACK ;
    }

    @Override
    public void onCreateAppView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_pass_list,container,false);

        groupWithPass =((MainActivity)getActivity()).getGroupsWithPass().get(((MainActivity)getActivity()).getGroupPosition());
                //((MainActivity)getActivity()).getGroupViewModel().getGroupsWithPass().getValue().get(((MainActivity)getActivity()).getGroupPosition());

        View rootView = getRootView();

        rvPassList = (RecyclerView) rootView.findViewById(R.id.rvPassList);
        final PassListAdapter passListAdapter = new PassListAdapter(rvPassList.getContext());
        passListAdapter.setParentActivity(getActivity());
        rvPassList.setAdapter(passListAdapter);
        rvPassList.setLayoutManager(new LinearLayoutManager(rvPassList.getContext()));

        passListAdapter.setPass(groupWithPass.passList);



        //ImageButton bAdd = (ImageButton) rootView.findViewById(R.id.bAdd);
        getButtonAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(true,true);
            }
        });

        //ImageButton bSearch = (ImageButton) rootView.findViewById(R.id.bSearch);
        getButtonSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showSearch();
            }
        });

        //ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        getButtonBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroupList();
            }
        });


        this.configureOnClickRecyclerView();

        //return rootView;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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

                        ((MainActivity)getActivity()).showPass(false,false,position);
                    }
                });
    }


}
