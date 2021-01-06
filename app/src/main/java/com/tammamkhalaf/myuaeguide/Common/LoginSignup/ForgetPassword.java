package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.tammamkhalaf.myuaeguide.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    Animation animation;
    private TextInputLayout phoneNumberTextField;
    private CountryCodePicker countryCodePicker;
    RelativeLayout progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_password);

        ImageView screenIcon = findViewById(R.id.forget_password_icon);
        TextView title = findViewById(R.id.forget_password_title);
        TextView description = findViewById(R.id.forget_password_description);
        phoneNumberTextField = findViewById(R.id.forget_password_phone_number);
        countryCodePicker = findViewById(R.id.country_code_picker);
        Button nextBtn = findViewById(R.id.forget_password_next_btn);
        progressBar = findViewById(R.id.progress_bar);


        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation);

        //Set animation to all the elements
        screenIcon.setAnimation(animation);
        title.setAnimation(animation);
        description.setAnimation(animation);
        phoneNumberTextField.setAnimation(animation);
        countryCodePicker.setAnimation(animation);
        nextBtn.setAnimation(animation);


    }

    public void callBackScreenFromForgetPassword(View view) {

    }

    public void verifyPhoneNumber(View view) {


        if (isConnected(this) && validateField()) {
            progressBar.setVisibility(View.VISIBLE);
            //Get complete phone number

            String _getUserEnteredPhoneNumber = Objects.requireNonNull(phoneNumberTextField.getEditText()).getText().toString().trim();
            //Remove first zero if entered!
            if (_getUserEnteredPhoneNumber.charAt(0) == '0') {
                _getUserEnteredPhoneNumber = _getUserEnteredPhoneNumber.substring(1);
            }
            //Complete phone number
            final String _phoneNo = "+" + countryCodePicker.getFullNumber() + _getUserEnteredPhoneNumber;

            Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(_phoneNo);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        phoneNumberTextField.setError(null);
                        phoneNumberTextField.setErrorEnabled(false);

                        Intent intent = new Intent(getApplicationContext(),VerifyOTP.class);
                        intent.putExtra("phoneNo",_phoneNo);
                        intent.putExtra("whatToDo","updateData");
                        startActivity(intent);
                        finish();
                        progressBar.setVisibility(View.GONE);


                    } else {
                        progressBar.setVisibility(View.GONE);
                        phoneNumberTextField.setError("No Such user Exist!");
                        phoneNumberTextField.requestFocus();
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        }

    }

    private boolean isConnected(ForgetPassword forgetPassword) {
        ConnectivityManager connectivityManager = (ConnectivityManager) forgetPassword.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        return wifiConnection != null && wifiConnection.isConnected() || (mobileConnection != null && mobileConnection.isConnected());

    }

    private boolean validateField() {
        String _phoneNumber = Objects.requireNonNull(phoneNumberTextField.getEditText()).getText().toString().trim();
        if (_phoneNumber.isEmpty()) {
            phoneNumberTextField.setError("Field can not be empty");
            phoneNumberTextField.requestFocus();
            return false;
        } else {
            return true;//todo check zero for phone number add at the beginnings
        }

    }
}