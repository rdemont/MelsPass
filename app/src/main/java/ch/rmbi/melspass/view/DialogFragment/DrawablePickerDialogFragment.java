package ch.rmbi.melspass.view.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.IconListSpinnerAdapter;
import ch.rmbi.melspass.utils.Icon;
import ch.rmbi.melspass.utils.IconList;

public class DrawablePickerDialogFragment extends DialogFragment {
    DrawablePickerDialogListener listener;

    public void setListener(DrawablePickerDialogFragment.DrawablePickerDialogListener listener) {
        this.listener = listener;
    }
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        List<Icon> icons = IconList.getInstance(getContext()).getIconList();
        View drawableView = getActivity().getLayoutInflater().inflate(R.layout.dialog_drawable_picker,null);
        Spinner spinner = drawableView.findViewById(R.id.spDrawable);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listener.onDrawablePickup(icons.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        IconListSpinnerAdapter adapter = new IconListSpinnerAdapter(getContext(), icons);
        spinner.setAdapter(adapter);

        // Set the dialog title
        builder.setTitle(R.string.DrawablePicker_Title)

                .setView(drawableView)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    ;
                    }
                });

        AlertDialog ad = builder.create();
        ad.getWindow().setLayout(100,50);

        return ad;

    }



    public interface DrawablePickerDialogListener {
        void onDrawablePickup(Icon icon);
    }
}
