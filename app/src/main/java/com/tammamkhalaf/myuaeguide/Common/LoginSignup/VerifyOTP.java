package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tammamkhalaf.myuaeguide.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    private static final String TAG = "VerifyOTP";
    PinView pinFromUser;
    String codeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verify_otp);

        pinFromUser = findViewById(R.id.pin_view);

        Intent intentPrevious = getIntent();
        String fullName = intentPrevious.getStringExtra("fullName");
        String username = intentPrevious.getStringExtra("username");
        String email = intentPrevious.getStringExtra("email");
        String password = intentPrevious.getStringExtra("password");
        String gender = intentPrevious.getStringExtra("gender");
        String date = intentPrevious.getStringExtra("age");
        String phoneNo = intentPrevious.getStringExtra("phoneNo");

        Log.i(TAG, "onCreate: phone Number"+phoneNo);
        sendVerificationCodeToUser(phoneNo);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Verification completed successfully here Either
                            // store the data or do whatever desire
                            Toast.makeText(VerifyOTP.this, "Verification Success", Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try again.out", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,// Activity (for callback binding) //todo find way for this rock TaskExecutors.MAIN_THREAD
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);//TODO REMAINING SET THE CODE DIRECTLY ONCE REACHED TO THE PHONE
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            };



    public void goToHomeFromOTP(View view) {
    }

    public void callNextScreenFromOTP(View view) {
        String code = pinFromUser.getText().toString();
        if(!code.isEmpty()){
            verifyCode(code);
        }
    }
}

