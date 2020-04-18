package ch.rmbi.melspass.view.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.view.MainActivity;

public class SearchDialogFragment extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());




        View searchView = getActivity().getLayoutInflater().inflate(R.layout.dialog_search,null);
        TextView etString = searchView.findViewById(R.id.etString);

        ImageButton bSearch = searchView.findViewById(R.id.bSearch);
        ImageButton bCancel = searchView.findViewById(R.id.bCancel);


        builder.setTitle(R.string.dialog_search_title)
            .setView(searchView);

        AlertDialog ad = builder.create();
        ad.getWindow().setLayout(50,50);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).showSearch(String.valueOf(etString.getText()));
                ad.dismiss();
            }

        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ad != null) {
                    ad.dismiss();
                }
            }
        });


        return ad;

    }
}
