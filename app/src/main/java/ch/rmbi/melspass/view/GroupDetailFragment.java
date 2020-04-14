package ch.rmbi.melspass.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Group;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.room.Repository;


public class GroupDetailFragment extends Fragment {

    Group group;
    //GroupWithPass groupWithPas;
    LiveData<List<GroupWithPass>> groups;
    int position =0;

    EditText etName;
    EditText etDescription;
    ImageView ivIcon;

    Repository repository ;

    boolean readWrite = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_detail,container,false);


        etName = (EditText)rootView.findViewById(R.id.etName);
        etName.setEnabled(readWrite);
        etDescription = (EditText)rootView.findViewById(R.id.etDescription);
        etDescription.setEnabled(readWrite);
        ivIcon = (ImageView)rootView.findViewById(R.id.ivIcon);

        if (group != null) {
            etName.setText(group.getName());
            etDescription.setText(group.getDescription());
        }
        repository = new Repository(getActivity().getApplication());

        ImageButton bCancel = (ImageButton) rootView.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(group,false,position);
            }
        });
        ImageButton bSave = (ImageButton) rootView.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group != null) {
                    group.setName(etName.getText().toString());
                    group.setDescription(etDescription.getText().toString());


                    repository.update(group);

                    ((MainActivity)getActivity()).show(group,false,position);
                }else {
                    group = new Group(
                            etName.getText().toString(),
                            etDescription.getText().toString()
                    );
                    repository.insert(group);

                    ((MainActivity)getActivity()).show(groups);
                }

            }
        });

        ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        bBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(groups);
            }
        });
        ImageButton bPrevious = (ImageButton) rootView.findViewById(R.id.bPrevious);
        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(group,false,--position);
            }
        });
        ImageButton bFirst = (ImageButton) rootView.findViewById(R.id.bFirst);
        bFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(group,false,0);
            }
        });
        ImageButton bNext = (ImageButton) rootView.findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(group,false,++position);
            }
        });

        ImageButton bLast = (ImageButton) rootView.findViewById(R.id.bLast);
        bLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(group,false,groups.getValue().size()-1);
            }
        });


        if (readWrite) {
            bCancel.setVisibility(View.VISIBLE);
            bSave.setVisibility(View.VISIBLE);

            bPrevious.setVisibility(View.INVISIBLE);
            bFirst.setVisibility(View.INVISIBLE);
            bNext.setVisibility(View.INVISIBLE);
            bLast.setVisibility(View.INVISIBLE);
            bBackPage.setVisibility(View.INVISIBLE);
        }else{
            bCancel.setVisibility(View.INVISIBLE);
            bSave.setVisibility(View.INVISIBLE);
            bBackPage.setVisibility(View.VISIBLE);
            if (position == 0)
            {
                bPrevious.setVisibility(View.INVISIBLE);
                bFirst.setVisibility(View.INVISIBLE);
            }else {
                bPrevious.setVisibility(View.VISIBLE);
                bFirst.setVisibility(View.VISIBLE);
            }

            if (position == groups.getValue().size()-1)
            {
                bNext.setVisibility(View.INVISIBLE);
                bLast.setVisibility(View.INVISIBLE);
            }else {
                bNext.setVisibility(View.VISIBLE);
                bLast.setVisibility(View.VISIBLE);
            }
        }


        return rootView;
    }


    public void updateGroup(Group grpObj, LiveData<List<GroupWithPass>> grps, int pos)
    {
        group = grpObj ;

        //groupWithPas = gWithp;
        groups = grps;
        position = pos;
    }

    public void setReadWrite(boolean rWrite)
    {
        readWrite = rWrite;
    }
}
