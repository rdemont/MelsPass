package ch.rmbi.melspass.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.Executor;

import ch.rmbi.melspass.R;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ch.rmbi.melspass.R.layout.activity_splash_screen);

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricManager biometricManager = BiometricManager.from(this);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                authUser(executor);
                break ;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE :
                Toast.makeText(
                        this,
                        getString(R.string.biometric_no_hardware) ,
                        Toast.LENGTH_LONG
                ).show();
                break ;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE :
                Toast.makeText(
                        this,
                        getString(R.string.biometric_hardware_unavailable),
                        Toast.LENGTH_LONG
                ).show();
                break ;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED :
                Toast.makeText(
                    this,
                    getString(R.string.biometric_not_setup),
                    Toast.LENGTH_LONG
                ).show();
        }

/*
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

 */
    }

    private void start(){
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    private void authUser(Executor executor) {
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setDeviceCredentialAllowed(true)
                .build();
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(
                        getApplicationContext(),
                        getString(R.string.biometric_auth_error),
                        Toast.LENGTH_LONG
                ).show();
                System.exit(0);

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                start();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.biometric_auth_error),
                        Toast.LENGTH_LONG
                ).show();
                System.exit(0);
            }

        });


        biometricPrompt.authenticate(promptInfo);
    }
}
