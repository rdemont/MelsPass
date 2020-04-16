package ch.rmbi.melspass.view;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
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
import ch.rmbi.melspass.view.DialogFragment.PasswordGeneratorDialogFragment;


public class PassDetailFragment extends Fragment implements PasswordGeneratorDialogFragment.PasswordGeneratorDialogListener {

    PassDetailFragment me = this;
    EditText etName;
    EditText etUserName;
    EditText etUserPass;
    EditText etUrl;
    EditText etDescription;
    Spinner sGroup;
    Pass pass = null;

    boolean readWrite = false;
    boolean isNew = false ;
    String groupsName[] ;
    Long groupsId[];

    //Repository repository ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pass_detail,container,false);

        if (!isNew) {
            pass = ((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).passList.get(((MainActivity) getActivity()).getPassPosition());
        }

        List<GroupWithPass> groups = ((MainActivity)getActivity()).getGroupsWithPass();

        groupsName = new String[groups.size()];
        groupsId = new Long[groups.size()];
        for (int i=0;i<groups.size();i++)
        {
            groupsName[i] = groups.get(i).group.getName();
            groupsId[i] = groups.get(i).group.getId();
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
        }


        for (int i = 0; i < groupsId.length; i++) {
            if (pass == null)
            {
                sGroup.setSelection(((MainActivity)getActivity()).getGroupPosition());
            }else {
                if (groupsId[i] == pass.getGroup_id()) {
                    sGroup.setSelection(i);
                }
            }


        }


        ImageButton bDelete = (ImageButton) rootView.findViewById(R.id.bDelete);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle(R.string.Delete);
                alert.setMessage(R.string.Delete_Msg);
                alert.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).getGroupViewModel().delete(pass);
                        if (((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).passList.size() <= 1)
                        {
                            ((MainActivity) getActivity()).showGroupList();
                        }else {
                            ((MainActivity) getActivity()).showPassList();
                        }
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).showPass(readWrite,isNew);
                        dialog.dismiss();
                    }
                });

                alert.create().show();
            }
        });
        ImageButton bEdit = (ImageButton) rootView.findViewById(R.id.bEdit);
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(true);
            }
        });
        ImageButton bCancel = (ImageButton) rootView.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(false);
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


                    ((MainActivity)getActivity()).getGroupViewModel().update(pass);


                }else {
                    pass = new Pass(
                            groupsId[sGroup.getSelectedItemPosition()] ,
                            etName.getText().toString(),
                            etUserName.getText().toString(),
                            etUserPass.getText().toString(),
                            etUrl.getText().toString(),
                            etDescription.getText().toString()
                    );
                    ((MainActivity)getActivity()).getGroupViewModel().insert(pass);
                }
                ((MainActivity)getActivity()).showPassList();

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

        ImageButton bPasswordGenerator = (ImageButton) rootView.findViewById(R.id.bPasswordGenerator);
        bPasswordGenerator.setVisibility(View.VISIBLE);
        if (!readWrite) {
            bPasswordGenerator.setVisibility(View.INVISIBLE);
        }
        bPasswordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordGeneratorDialogFragment dialog = new PasswordGeneratorDialogFragment();
                dialog.setListener(me);
                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");


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
                ((MainActivity)getActivity()).showPassList();
            }
        });
        ImageButton bPrevious = (ImageButton) rootView.findViewById(R.id.bPrevious);
        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(readWrite,false,((MainActivity) getActivity()).getPassPosition()-1);
            }
        });
        ImageButton bFirst = (ImageButton) rootView.findViewById(R.id.bFirst);
        bFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(readWrite,false,0);
            }
        });
        ImageButton bNext = (ImageButton) rootView.findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(readWrite,false,((MainActivity) getActivity()).getPassPosition()+1);
            }
        });

        ImageButton bLast = (ImageButton) rootView.findViewById(R.id.bLast);
        bLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showPass(readWrite,false,((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).passList.size()-1);
            }
        });


        if (readWrite) {
            bCancel.setVisibility(View.VISIBLE);
            bSave.setVisibility(View.VISIBLE);

            bDelete.setVisibility(View.INVISIBLE);
            bEdit.setVisibility(View.INVISIBLE);
            bPrevious.setVisibility(View.INVISIBLE);
            bFirst.setVisibility(View.INVISIBLE);
            bNext.setVisibility(View.INVISIBLE);
            bLast.setVisibility(View.INVISIBLE);
            bBackPage.setVisibility(View.INVISIBLE);
        }else{
            bCancel.setVisibility(View.INVISIBLE);
            bSave.setVisibility(View.INVISIBLE);

            bDelete.setVisibility(View.VISIBLE);
            bEdit.setVisibility(View.VISIBLE);
            bBackPage.setVisibility(View.VISIBLE);
            if (((MainActivity) getActivity()).getPassPosition() == 0)
            {
                bPrevious.setVisibility(View.INVISIBLE);
                bFirst.setVisibility(View.INVISIBLE);
            }else {
                bPrevious.setVisibility(View.VISIBLE);
                bFirst.setVisibility(View.VISIBLE);
            }

            if (((MainActivity) getActivity()).getPassPosition() == ((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).passList.size()-1)
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

    public void setReadWrite(boolean rWrite)
    {
        readWrite = rWrite;
    }
    public void setisNew(boolean isNe) {isNew = isNe;}

    @Override
    public void onPasswordGenerate(char[] password) {
        if ((etUserPass != null) && (readWrite))
            etUserPass.setText(String.valueOf(password));
    }
}
