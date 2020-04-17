package ch.rmbi.melspass.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.room.Group;
import ch.rmbi.melspass.utils.Icon;
import ch.rmbi.melspass.utils.IconList;
import ch.rmbi.melspass.view.DialogFragment.DrawablePickerDialogFragment;


public class GroupDetailFragment extends TemplateFragment implements DrawablePickerDialogFragment.DrawablePickerDialogListener {

    Group group;

    EditText etName;
    EditText etDescription;
    ImageView ivIcon;
    Icon icon ;


    boolean readWrite = false;
    boolean isNew = false ;

    GroupDetailFragment me = this;


    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_group_detail;
    }

    @Override
    protected String getFragmentTitle() {
        return "Edition";
    }


    @Override
    protected int getFragmentButtonVisible() {
        return showButton;
    }
    int showButton = 0;
    public void onCreateAppView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //View rootView = inflater.inflate(R.layout.fragment_group_detail,container,false);

        if (!isNew) {
            group = ((MainActivity) getActivity()).getGroupsWithPass().get(((MainActivity) getActivity()).getGroupPosition()).group;
        }
        View rootView = getRootView();

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
        //ImageButton bDelete = (ImageButton) rootView.findViewById(R.id.bDelete);
        getButtonDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle(R.string.Delete);
                alert.setMessage(R.string.Delete_Msg);
                alert.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).showGroup(readWrite,false);
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).getViewModel().delete(group);
                        ((MainActivity)getActivity()).setWaitingLiveDate(true);
                        ((MainActivity)getActivity()).setWaitingNext(MainActivity.NEXT_GROUP_LIST);
                        dialog.dismiss();
                    }
                });


                alert.create().show();
            }
        });


        //ImageButton bEdit = (ImageButton) rootView.findViewById(R.id.bEdit);
        getButtonEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(true,isNew);
            }
        });

        //ImageButton bCancel = (ImageButton) rootView.findViewById(R.id.bCancel);
        getButtonCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNew) {
                    ((MainActivity)getActivity()).showGroupList();
                }else{
                    ((MainActivity)getActivity()).showGroup(false,isNew);
                }

            }
        });
        //ImageButton bSave = (ImageButton) rootView.findViewById(R.id.bSave);
        getButtonSave().setOnClickListener(new View.OnClickListener() {
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
                    ((MainActivity)getActivity()).getViewModel().update(group);
                }else {


                    group = new Group(
                            etName.getText().toString(),
                            etDescription.getText().toString(),
                            iicon
                    );
                    ((MainActivity)getActivity()).getViewModel().insert(group);
                }
                ((MainActivity)getActivity()).setWaitingNext(MainActivity.NEXT_GROUP_LIST);
                ((MainActivity)getActivity()).setWaitingLiveDate(true);

            }
        });

        //ImageButton bBackPage = (ImageButton) rootView.findViewById(R.id.bBackPage);
        getButtonBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroupList();
            }
        });
        //ImageButton bPrevious = (ImageButton) rootView.findViewById(R.id.bPrevious);
        getButtonPrevious().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupPosition()-1);
            }
        });

        //ImageButton bFirst = (ImageButton) rootView.findViewById(R.id.bFirst);
        getButtonFirst().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,0);
            }
        });
        //ImageButton bNext = (ImageButton) rootView.findViewById(R.id.bNext);
        getButtonNext().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupPosition()+1);
            }
        });

        //ImageButton bLast = (ImageButton) rootView.findViewById(R.id.bLast);
        getButtonLast().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showGroup(false,false,((MainActivity)getActivity()).getGroupsWithPass().size()-1);
            }
        });

        showButton = 0 ;
        if (readWrite) {
            showButton = showButton | SHOW_BUTTON_CANCEL | SHOW_BUTTON_SAVE;
        }else{
            showButton = showButton | SHOW_BUTTON_EDIT | SHOW_BUTTON_BACK | SHOW_BUTTON_SEARCH;
            if (((MainActivity)getActivity()).getGroupsWithPass().get(((MainActivity)getActivity()).getGroupPosition()).passList.size() <= 0)
            {
                showButton = showButton | SHOW_BUTTON_DELETE;

            }

            if (((MainActivity)getActivity()).getGroupPosition() > 0) {
                showButton = showButton | SHOW_BUTTON_PREVIOUS | SHOW_BUTTON_FIRST;
            }
            if (((MainActivity)getActivity()).getGroupPosition() < ((MainActivity)getActivity()).getGroupsWithPass().size()-1)
            {
                showButton = showButton | SHOW_BUTTON_NEXT | SHOW_BUTTON_LAST;

            }
        }


        //return rootView;
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
