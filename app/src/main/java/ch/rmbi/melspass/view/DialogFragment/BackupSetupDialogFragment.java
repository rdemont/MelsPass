package ch.rmbi.melspass.view.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Date;
import java.util.List;

import ch.rmbi.melspass.R;
import ch.rmbi.melspass.adapter.PassListSpinnerAdapter;
import ch.rmbi.melspass.room.Pass;
import ch.rmbi.melspass.room.PassWithGroup;
import ch.rmbi.melspass.room.Setup;
import ch.rmbi.melspass.view.MainActivity;
import ch.rmbi.melspass.view.PassDetailFragment;

public class BackupSetupDialogFragment extends DialogFragment implements PasswordGeneratorDialogFragment.PasswordGeneratorDialogListener {

    BackupSetupDialogFragment me = this;
    Setup setup ;
    List<PassWithGroup> passWithGroupList;
    EditText etCryptoKey ;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        setup = ((MainActivity)getActivity()).getViewModel().getSetup();
        if(setup == null)
        {
            setup = new Setup(false, false,null,"","",(new Date()).getTime());
            ((MainActivity)getActivity()).getViewModel().insert(setup);
        }

        View drawableView = getActivity().getLayoutInflater().inflate(R.layout.dialog_backup_setup,null);

        passWithGroupList = ((MainActivity)getActivity()).getViewModel().getPassWithGroup() ;

        CheckBox cbLocalBackup = drawableView.findViewById(R.id.cbLocalBackup);
        cbLocalBackup.setChecked(setup.isLocalBackup());

        CheckBox cbFTPBackup = drawableView.findViewById(R.id.cbFTPBackup);
        cbFTPBackup.setChecked(setup.isFtpBackup());

        EditText etFtpFolder = drawableView.findViewById(R.id.etFtpFolder);
        etFtpFolder.setText(setup.getFtpRemoteFolder());
        etCryptoKey = drawableView.findViewById(R.id.etCryptoKey);
        etCryptoKey.setText(setup.getCryptoKey());

        ImageButton bShowUserPass = drawableView.findViewById(R.id.bShowUserPass);
        bShowUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCryptoKey.getTransformationMethod() == PasswordTransformationMethod.getInstance())
                {
                    etCryptoKey.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    etCryptoKey.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        ImageButton bPasswordGenerator = drawableView.findViewById(R.id.bPasswordGenerator);
        bPasswordGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordGeneratorDialogFragment dialog = new PasswordGeneratorDialogFragment();
                dialog.setListener(me);
                dialog.show(getParentFragmentManager(), "NoticeDialogFragment");


            }
        });



        Spinner spinner = drawableView.findViewById(R.id.spPass);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Salut",Toast.LENGTH_LONG);
                //listener.onPassPickup(passList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        PassListSpinnerAdapter adapter = new PassListSpinnerAdapter( getContext(),passWithGroupList);
        spinner.setAdapter(adapter);


        for (int i = 0 ; i<passWithGroupList.size();i++)
        {
            if (passWithGroupList.get(i).pass.getId() == setup.getFtp_pass_id()){
                spinner.setSelection(i);
            }
        }

        // Set the dialog title
        builder.setTitle("Backup configuration")
                .setView(drawableView)
                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ;
                    }
                })
                .setPositiveButton("Sauver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setup.setLocalBackup(cbLocalBackup.isChecked());
                        setup.setFtpBackup(cbFTPBackup.isChecked());
                        if (passWithGroupList.size() > 0) {
                            setup.setFtp_pass_id(passWithGroupList.get(spinner.getSelectedItemPosition()).pass.getId());
                        }
                        setup.setLastChange(new Date());
                        setup.setFtpRemoteFolder(etFtpFolder.getText().toString());
                        setup.setCryptoKey(etCryptoKey.getText().toString());


                        ((MainActivity)getActivity()).getViewModel().update(setup);
                    }
                });

        AlertDialog ad = builder.create();
        ad.getWindow().setLayout(100,50);

        return ad;
    }

    @Override
    public void onPasswordGenerate(char[] password) {
        if (etCryptoKey != null) {
            etCryptoKey.setText(String.valueOf(password));
        }
    }
}
