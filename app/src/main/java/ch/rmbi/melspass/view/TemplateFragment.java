package ch.rmbi.melspass.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.utils.ItemClickSupport;
import ch.rmbi.melspass.view.DialogFragment.BackupSetupDialogFragment;
import ch.rmbi.melspass.view.DialogFragment.DrawablePickerDialogFragment;
import ch.rmbi.melspass.view.DialogFragment.SearchDialogFragment;

public abstract class TemplateFragment extends Fragment {
    protected int SHOW_BUTTON_NONE = 0;
    protected int SHOW_BUTTON_FIRST = 1;
    protected int SHOW_BUTTON_PREVIOUS = 2;
    protected int SHOW_BUTTON_SAVE = 4;
    protected int SHOW_BUTTON_EDIT = 8;
    protected int SHOW_BUTTON_ADD = 16;
    protected int SHOW_BUTTON_BACK = 32;
    protected int SHOW_BUTTON_SEARCH = 64;
    protected int SHOW_BUTTON_CANCEL = 128;
    protected int SHOW_BUTTON_DELETE = 256;
    protected int SHOW_BUTTON_NEXT = 512;
    protected int SHOW_BUTTON_LAST = 1024;


    abstract protected int getFragmentLayout();
    abstract protected String getFragmentTitle();
    abstract protected void onCreateAppView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) ;
    abstract protected int getFragmentButtonVisible();
    abstract protected void onBackPressed();



    private View rootView;
    TemplateFragment me = this;

    private ImageButton bSetupMenu;
    private ImageButton bAppFirst;
    private ImageButton bAppPrevious;
    private ImageButton bAppSave;
    private ImageButton bAppEdit;
    private ImageButton bAppAdd;
    private ImageButton bAppBack;
    private ImageButton bAppSearch;
    private ImageButton bAppCancel;
    private ImageButton bAppDelete;
    private ImageButton bAppNext;
    private ImageButton bAppLast;

    protected ImageButton getButtonFirst() {
        return bAppFirst;
    }

    protected ImageButton getButtonPrevious() {
        return bAppPrevious;
    }

    protected ImageButton getButtonSave() {
        return bAppSave;
    }

    protected ImageButton getButtonEdit() {
        return bAppEdit;
    }

    protected ImageButton getButtonAdd() {
        return bAppAdd;
    }

    protected ImageButton getButtonBack() {
        return bAppBack;
    }

    /*protected ImageButton getButtonSearch() {
        return bAppSearch;
    }*/

    protected ImageButton getButtonCancel() {
        return bAppCancel;
    }

    protected ImageButton getButtonDelete() {
        return bAppDelete;
    }

    protected ImageButton getButtonNext() {
        return bAppNext;
    }

    protected ImageButton getButtonLast() {
        return bAppLast;
    }


    private boolean isMainLayout = false ;
    protected boolean getIsMainLayout() {
        return isMainLayout ;
    }
    protected boolean getIsDetailLayout() {
        return ! isMainLayout;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View appView = inflater.inflate(R.layout.fragment_template, container, false);

        isMainLayout = container.getId() == R.id.frame_layout_main;




        ViewStub stub = appView.findViewById(R.id.vsApp);
        stub.setLayoutResource(getFragmentLayout());
        rootView = stub.inflate();

        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                onBackPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        TextView tvTitle =  appView.findViewById(R.id.tvAppTitle);


        bSetupMenu = appView.findViewById(R.id.bSetupMenu);
        bAppFirst = appView.findViewById(R.id.bAppFirst);
        bAppPrevious = appView.findViewById(R.id.bAppPrevious);
        bAppSave = appView.findViewById(R.id.bAppSave);
        bAppEdit = appView.findViewById(R.id.bAppEdit);
        bAppAdd = appView.findViewById(R.id.bAppAdd);
        bAppBack = appView.findViewById(R.id.bAppBack);
        bAppSearch = appView.findViewById(R.id.bAppSearch);
        bAppCancel = appView.findViewById(R.id.bAppCancel);
        bAppDelete = appView.findViewById(R.id.bAppDelete);
        bAppNext = appView.findViewById(R.id.bAppNext);
        bAppLast = appView.findViewById(R.id.bAppLast);


        if (((MainActivity)getActivity()).getIsLargeScreen() && getIsMainLayout())
        {
            bSetupMenu.setVisibility(View.INVISIBLE);
        }else {
            bSetupMenu.setVisibility(View.VISIBLE);
        }

        bAppFirst.setVisibility(View.INVISIBLE);
        bAppPrevious.setVisibility(View.INVISIBLE);
        bAppSave.setVisibility(View.INVISIBLE);
        bAppEdit.setVisibility(View.INVISIBLE);
        bAppAdd.setVisibility(View.INVISIBLE);
        bAppBack.setVisibility(View.INVISIBLE);
        bAppSearch.setVisibility(View.INVISIBLE);
        bAppCancel.setVisibility(View.INVISIBLE);
        bAppDelete.setVisibility(View.INVISIBLE);
        bAppNext.setVisibility(View.INVISIBLE);
        bAppLast.setVisibility(View.INVISIBLE);


        bSetupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(),v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_backup_setup:
                                BackupSetupDialogFragment dialog = new BackupSetupDialogFragment();
                                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");
                                break ;
                            case R.id.menu_do_backup :
                                //ToDo backup
                                break ;
                            case R.id.menu_do_restore :
                                //ToDo restore
                                break ;
                        }
                        return false;
                    }
                });
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.main_menu,popup.getMenu());
                popup.show();
            }
        });


        bAppSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchDialogFragment dialog = new SearchDialogFragment();

                //dialog.setListener(me);
                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");
            }
        });

        onCreateAppView(inflater,container,savedInstanceState);


        tvTitle.setText(getFragmentTitle());

        int bVisible = getFragmentButtonVisible();

        if ((bVisible & SHOW_BUTTON_FIRST) == SHOW_BUTTON_FIRST){bAppFirst.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_PREVIOUS) == SHOW_BUTTON_PREVIOUS){bAppPrevious.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_SAVE) == SHOW_BUTTON_SAVE){bAppSave.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_EDIT) == SHOW_BUTTON_EDIT){bAppEdit.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_ADD) == SHOW_BUTTON_ADD){bAppAdd.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_BACK) == SHOW_BUTTON_BACK){bAppBack.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_SEARCH) == SHOW_BUTTON_SEARCH){bAppSearch.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_CANCEL) == SHOW_BUTTON_CANCEL){bAppCancel.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_DELETE) == SHOW_BUTTON_DELETE){bAppDelete.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_NEXT) == SHOW_BUTTON_NEXT){bAppNext.setVisibility(View.VISIBLE);}
        if ((bVisible & SHOW_BUTTON_LAST) == SHOW_BUTTON_LAST){bAppLast.setVisibility(View.VISIBLE);}

        return appView;
    }

    protected View getRootView() {
        return rootView;
    }

}
