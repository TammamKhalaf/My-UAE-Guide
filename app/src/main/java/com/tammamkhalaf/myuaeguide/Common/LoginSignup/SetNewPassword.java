package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tammamkhalaf.myuaeguide.R;

import java.util.Objects;

public class SetNewPassword extends AppCompatActivity {

    Animation animation;
    TextInputLayout confirmPassword,newPassword;

    String newPasswordFinal,confirmedPassword;
    RelativeLayout progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);


        confirmPassword = findViewById(R.id.confirm_password);
        newPassword = findViewById(R.id.new_password);

        newPasswordFinal = newPassword.getEditText().getText().toString().trim();
        confirmedPassword = confirmPassword.getEditText().getText().toString().trim();

        progress_bar = findViewById(R.id.progress_bar);

        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        //Set animation to all the elements
//        screenIcon.setAnimation(animation);
//        title.setAnimation(animation);
//        description.setAnimation(animation);
//        phoneNumberTextField.setAnimation(animation);
//        countryCodePicker.setAnimation(animation);
//        nextBtn.setAnimation(animation);
    }

    public void goToHomeFromSetNewPassword(View view) {
    }

    public void setNewPasswordBtn(View view) {
        if(!isConnected((this))){
            showCustomDialog();
        }

        if(!validateFields()){
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);

        String phoneNo = getIntent().getStringExtra("phoneNo");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(phoneNo).child("password").setValue(confirmedPassword);

        startActivity(new Intent(SetNewPassword.this,ForgetPasswordSuccessMessage.class));
        finish();
    }
    private void showCustomDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetNewPassword.this);
        builder.setMessage("Please Connect To The Internet to connect further!")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            startActivity(new Intent(getApplicationContext(),RetailerStartUpScreen.class));
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean isConnected(SetNewPassword setNewPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) setNewPassword.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if (wifiConnection != null && wifiConnection.isConnected() || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateFields() {
        if (newPasswordFinal.isEmpty()) {
            newPassword.setError("Field can not be empty");
            newPassword.requestFocus();
            return false;
        } else if(confirmedPassword.isEmpty()) {
            confirmPassword.setError("Field can not be empty");
            confirmPassword.requestFocus();
            return false;//todo check zero for phone number add at the beginnings
        }else if(!newPasswordFinal.equals(confirmedPassword)){
            newPassword.requestFocus();
            newPassword.setError("Both should be same");
            confirmPassword.requestFocus();
            confirmPassword.setError("Both should be same");
            return false;
        }else {
            return true;
        }
    }

}