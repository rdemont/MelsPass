package ch.rmbi.melspass.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import ch.rmbi.melspass.utils.Icon;
import ch.rmbi.melspass.utils.IconList;
import ch.rmbi.melspass.view.DialogFragment.DrawablePickerDialogFragment;
import ch.rmbi.melspass.view.DialogFragment.PasswordGeneratorDialogFragment;


public class GroupDetailFragment extends Fragment implements DrawablePickerDialogFragment.DrawablePickerDialogListener {

    Group group;

    EditText etName;
    EditText etDescription;
    ImageView ivIcon;
    Icon icon ;


    boolean readWrite = false;
    boolean isNew = false ;

    GroupDetailFragment me = this;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_detail,container,false);

        if (!isNew) {
            group = ((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).group;
        }
        etName = (EditText)rootView.findViewById(R.id.etName);
        etName.setEnabled(readWrite);
        etDescription = (EditText)rootView.findViewById(R.id.etDescription);
        etDescription.setEnabled(readWrite);
        ivIcon = (ImageView)rootView.findViewById(R.id.ivIcon);

        ivIcon.setImageDrawable(IconList.getInstance(getContext()).getDefaultIcon().getDrawable());
        ivIcon.setContentDescription(IconList.getInstance(getContext()).getDefaultIcon().getDescription());

        if (group != null) {
            etName.setText(group.getName());
            etDescription.setText(group.getDescription());
            Icon icon = IconList.getInstance(getContext()).getIcon(group.getImageIndex());
            ivIcon.setImageDrawable(icon.getDrawable());
            ivIcon.setContentDescription(icon.getDescription());

        }

        if (readWrite) {
            ivIcon.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawablePickerDialogFragment dialog = new DrawablePickerDialogFragment();

                    dialog.setListener(me);
                    dialog.show(getParentFragmentManager(), "NoticeDialogFragment");


                }
            });
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
                        ((MainActivity)getActivity()).getGroupViewModel().delete(group);
                        ((MainActivity)getActivity()).showGroupList();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).showGroup(readWrite,false);
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
                ((MainActivity)getActivity()).showGroup(true,isNew);
            }
        });

        ImageButton bCancel = (ImageButton) rootView.findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,isNew);
            }
        });
        ImageButton bSave = (ImageButton) rootView.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iicon = -1 ;
                if (icon != null)
                {
                    iicon = icon.getIndex();
                }
                if (group != null) {
                    group.setName(etName.getText().toString());
                    group.setDescription(etDescription.getText().toString());
                    group.setImageIndex(iicon);
                    ((MainActivity)getActivity()).getGroupViewModel().update(group);
                }else {


                    group = new Group(
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            iicon
                    );
                    ((MainActivity)getActivity()).getGroupViewModel().insert(group);
                }
                ((MainActivity)getActivity()).showGroupList();

            }
        });

        ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        bBackPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroupList();
            }
        });
        ImageButton bPrevious = (ImageButton) rootView.findViewById(R.id.bPrevious);
        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupPosition()-1);
            }
        });
        ImageButton bFirst = (ImageButton) rootView.findViewById(R.id.bFirst);
        bFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,0);
            }
        });
        ImageButton bNext = (ImageButton) rootView.findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupPosition()+1);
            }
        });

        ImageButton bLast = (ImageButton) rootView.findViewById(R.id.bLast);
        bLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupsWithPass().size()-1);
            }
        });


        if (readWrite) {
            bCancel.setVisibility(View.VISIBLE);
            bSave.setVisibility(View.VISIBLE);
            bEdit.setVisibility(View.INVISIBLE);
            bDelete.setVisibility(View.INVISIBLE);

            bPrevious.setVisibility(View.INVISIBLE);
            bFirst.setVisibility(View.INVISIBLE);
            bNext.setVisibility(View.INVISIBLE);
            bLast.setVisibility(View.INVISIBLE);
            bBackPage.setVisibility(View.INVISIBLE);
        }else{
            bCancel.setVisibility(View.INVISIBLE);
            bSave.setVisibility(View.INVISIBLE);
            bEdit.setVisibility(View.VISIBLE);
            bDelete.setVisibility(View.INVISIBLE);
            if (((MainActivity)getActivity()).getGroupsWithPass().get(((MainActivity)getActivity()).getGroupPosition()).passList.size() <= 0)
            {
                bDelete.setVisibility(View.VISIBLE);
            }

            bBackPage.setVisibility(View.VISIBLE);
            if (((MainActivity)getActivity()).getGroupPosition() == 0)
            {
                bPrevious.setVisibility(View.INVISIBLE);
                bFirst.setVisibility(View.INVISIBLE);
            }else {
                bPrevious.setVisibility(View.VISIBLE);
                bFirst.setVisibility(View.VISIBLE);
            }

            if (((MainActivity)getActivity()).getGroupPosition() == ((MainActivity)getActivity()).getGroupsWithPass().size()-1)
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

    public void setisNew(boolean isNe)
    {
        isNew = isNe;
    }


    public void setReadWrite(boolean rWrite)
    {
        readWrite = rWrite;
    }

    @Override
    public void onDrawablePickup(Icon icon) {
        ivIcon.setImageDrawable(icon.getDrawable());
        this.icon = icon ;


    }
}
