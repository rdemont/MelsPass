package ch.rmbi.melspass.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.PassListAdapter;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.utils.ItemClickSupport;

public class SearchResultFragment extends TemplateFragment {

    @Override
    protected int getFragmentLayout() {
   return R.layout.fragment_search_result;
  }

    @Override
    protected String getFragmentTitle() {
   return title;
  }
    String title = "";

    private RecyclerView rvPassList;

    private PassListAdapter passListAdapter;

    @Override
    protected void onCreateAppView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        List<Pass> passList =((MainActivity)getActivity()).getSearchPassList();


        View rootView = getRootView();


        rvPassList = (RecyclerView) rootView.findViewById(R.id.rvPassList);
        passListAdapter = new PassListAdapter(rvPassList.getContext());
        passListAdapter.setParentActivity(getActivity());

        if ((passList == null) || (passList.size() <= 0)){
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle(R.string.dialog_search_title);
            alert.setMessage("Aucun resulat");
            alert.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ((MainActivity)getActivity()).showGroupList();
                }
            });
            alert.create();
            alert.show();
        }

        rvPassList.setAdapter(passListAdapter);
        rvPassList.setLayoutManager(new LinearLayoutManager(rvPassList.getContext()));

        passListAdapter.setPass(passList);

        title = getResources().getString(R.string.list_search_result_fragment_title);
        //title = title + " " +groupWithPass.group.getName();



        //ImageButton bAdd = (ImageButton) rootView.findViewById(R.id.bAdd);
        getButtonAdd().setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
          ((MainActivity)getActivity()).showPass(true,true);
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
    protected int getFragmentButtonVisible() {
        if (((MainActivity) getActivity()).getIsLargeScreen())
        {
            return SHOW_BUTTON_NONE ;
        }else {
            return SHOW_BUTTON_SEARCH | SHOW_BUTTON_BACK;
        }
    }

    @Override
    protected void onBackPressed() {
       ((MainActivity) getActivity()).showGroupList();

    }


    View vSelected ;
    private void configureOnClickRecyclerView(){
     ItemClickSupport.addTo(rvPassList, R.layout.pass_list_layout)
             .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
              @Override
              public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                  if (((MainActivity)getActivity()).getIsLargeScreen() && getIsMainLayout() ){
                      ((MainActivity)getActivity()).showSearchPass(position);
                  }else {
                      if ((vSelected == null) && (passListAdapter != null)) {
                          passListAdapter.getViewSelected().setSelected(false);
                          vSelected = v;
                          vSelected.setSelected(true);
                      } else {
                          vSelected.setSelected(false);
                          vSelected = v;
                          vSelected.setSelected(true);
                      }
                  }


              }
             });
    }
}
