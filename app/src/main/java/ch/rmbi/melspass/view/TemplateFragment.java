package ch.rmbi.melspass.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.utils.ItemClickSupport;

public abstract class TemplateFragment extends Fragment {

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

    private View rootView;

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

    protected ImageButton getButtonSearch() {
        return bAppSearch;
    }

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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View appView = inflater.inflate(R.layout.fragment_template, container, false);

        ViewStub stub = appView.findViewById(R.id.vsApp);
        stub.setLayoutResource(getFragmentLayout());
        rootView = stub.inflate();



        TextView tvTitle =  appView.findViewById(R.id.tvAppTitle);


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
