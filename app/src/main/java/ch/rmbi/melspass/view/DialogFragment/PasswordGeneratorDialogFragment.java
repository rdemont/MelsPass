package ch.rmbi.melspass.view.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ch.rmbi.melspass.R;
import ch.rmbi.melspass.utils.PasswordGenerator;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class PasswordGeneratorDialogFragment extends DialogFragment {
    PasswordGeneratorDialogListener listener;
    int passwordType = 0;
    int passwordLength = 8;

    public void setListener(PasswordGeneratorDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View passwordView = getActivity().getLayoutInflater().inflate(R.layout.dialog_password_generator,null);
        TextView tvValue = passwordView.findViewById(R.id.tvValue);
        SeekBar sbPasswordLength = passwordView.findViewById(R.id.sbPasswordLength);
        sbPasswordLength.setMax(20);
        sbPasswordLength.setMin(6);
        sbPasswordLength.setProgress(12);
        tvValue.setText(String.valueOf(sbPasswordLength.getProgress()));
        sbPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Set the dialog title
        builder.setTitle(ch.rmbi.melspass.R.string.dialog_password_title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(new String[]{getResources().getString(R.string.dialog_password_uppercase)
                                , getResources().getString(R.string.dialog_password_lowercase)
                                ,getResources().getString(R.string.dialog_password_number)
                                ,getResources().getString(R.string.dialog_password_specialchar)
                                }, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    switch (which) {
                                        case 0:
                                            passwordType = passwordType | PasswordGenerator.USE_UPPERCASE;
                                            break;
                                        case 1:
                                            passwordType = passwordType | PasswordGenerator.USE_LOWERCASE;
                                            break;
                                        case 2:
                                            passwordType = passwordType | PasswordGenerator.USE_NUMBER;
                                            break;
                                        case 3:
                                            passwordType = passwordType | PasswordGenerator.USE_SEPCIALCHAR;
                                            break;
                                    }
                                }
                            }
                        })
                .setView(passwordView)
                // Set the action buttons
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog

                        if (listener != null) {
                            listener.onPasswordGenerate(PasswordGenerator.getInstance().getRandomPassword(sbPasswordLength.getProgress(), passwordType));
                        }
                    }
                })
                .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ;
                    }
                });

        return builder.create();
    }

    public interface PasswordGeneratorDialogListener {
        void onPasswordGenerate(char[] password);
    }

}
