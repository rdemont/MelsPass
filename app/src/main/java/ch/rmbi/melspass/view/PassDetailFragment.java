package ch.rmbi.melspass.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Group;
import ch.rmbi.melspass.room.GroupWithPass;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.room.Repository;


public class PassDetailFragment extends Fragment {

    EditText etName;
    EditText etUserName;
    EditText etUserPass;
    EditText etUrl;
    EditText etDescription;
    Spinner sGroup;
    Pass pass;
    GroupWithPass groupWithPas;
    int position =0;
    boolean readWrite = false;
    String groupsName[] ;
    Long groupsId[];

    Repository repository ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pass_detail,container,false);



        repository = new Repository(getActivity().getApplication());

        List<Group> groups = repository.getGroups();
        groupsName = new String[groups.size()];
        groupsId = new Long[groups.size()];
        for (int i=0;i<groups.size();i++)
        {
            groupsName[i] = groups.get(i).getName();
            groupsId[i] = groups.get(i).getId();
        }

        etName = (EditText)rootView.findViewById(R.id.etName);
        etName.setEnabled(readWrite);
        etUserName = (EditText)rootView.findViewById(R.id.etUserName);
        etUserName.setEnabled(readWrite);
        etUserPass = (EditText)rootView.findViewById(R.id.etUserPass);
        etUserPass.setEnabled(readWrite);
        etUrl = (EditText)rootView.findViewById(R.id.etUrl);
        etUrl.setEnabled(readWrite);
        etDescription = (EditText)rootView.findViewById(R.id.etDescription);
        etDescription.setEnabled(readWrite);
        sGroup = (Spinner)rootView.findViewById(R.id.sGroup);
        sGroup.setEnabled(readWrite);


        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, groupsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGroup.setAdapter(adapter);

        if (pass != null) {
            etName.setText(pass.getName());
            etUserName.setText(pass.getUsername());
            etUserPass.setText(pass.getUserPass());
            etUrl.setText(pass.getUrl());
            etDescription.setText(pass.getDescription());

            for (int i = 0; i < groupsId.length; i++) {
                if (groupsId[i] == pass.getGroup_id())
                    sGroup.setSelection(i);
            }
        }


        ImageButton bCancel = (ImageButton) rootView.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(pass,false,position);
            }
        });
        ImageButton bSave = (ImageButton) rootView.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass != null) {
                    pass.setName(etName.getText().toString());
                    pass.setUsername(etUserName.getText().toString());
                    pass.setUserPass(etUserPass.getText().toString());
                    pass.setUrl(etUrl.getText().toString());
                    pass.setDescription(etDescription.getText().toString());
                    pass.setGroup_id(groupsId[sGroup.getSelectedItemPosition()]);


                    repository.update(pass);

                    ((MainActivity)getActivity()).show(pass,false,position);
                }else {
                    pass = new Pass(
                            groupsId[sGroup.getSelectedItemPosition()] ,
                            etName.getText().toString(),
                            etUserName.getText().toString(),
                            etUserPass.getText().toString(),
                            etUrl.getText().toString(),
                            etDescription.getText().toString()
                    );
                    repository.insert(pass);
                    ((MainActivity)getActivity()).show();
                }

            }
        });

        ImageButton bCopyUsername = (ImageButton) rootView.findViewById(R.id.bCopyUserName);
        bCopyUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("UserName",etUserName.getText());
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(getContext().getApplicationContext().CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
            }
        });

        ImageButton bCopyUserPass = (ImageButton) rootView.findViewById(R.id.bCopyUserPass);
        bCopyUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("UserPass",etUserPass.getText());
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(getContext().getApplicationContext().CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
            }
        });

        ImageButton bCopyUrl = (ImageButton) rootView.findViewById(R.id.bCopyUrl);
        bCopyUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("Url",etUrl.getText());
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(getContext().getApplicationContext().CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(clip);
            }
        });

        ImageButton bShowUserPass = (ImageButton) rootView.findViewById(R.id.bShowUserPass);
        bShowUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserPass.getTransformationMethod() == PasswordTransformationMethod.getInstance())
                {
                    etUserPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etUserPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        ImageButton bOpenUrl = (ImageButton) rootView.findViewById(R.id.bOpenUrl);
        bOpenUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(etUrl.getText().toString()));
                startActivity(browserIntent);
            }
        });
        ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        bBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(groupWithPas);
            }
        });
        ImageButton bPrevious = (ImageButton) rootView.findViewById(R.id.bPrevious);
        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(pass,false,--position);
            }
        });
        ImageButton bFirst = (ImageButton) rootView.findViewById(R.id.bFirst);
        bFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(pass,false,0);
            }
        });
        ImageButton bNext = (ImageButton) rootView.findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(pass,false,++position);
            }
        });

        ImageButton bLast = (ImageButton) rootView.findViewById(R.id.bLast);
        bLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).show(pass,false,groupWithPas.passList.size()-1);
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

            if (position == groupWithPas.passList.size()-1)
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

    public void updatePass(Pass pssObj,GroupWithPass gWithp,int pos)
    {
        pass = pssObj ;
        groupWithPas = gWithp;
        position = pos;
    }

    public void setReadWrite(boolean rWrite)
    {
        readWrite = rWrite;
    }
}
