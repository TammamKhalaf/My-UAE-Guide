package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.tammamkhalaf.myuaeguide.R;

public class SignUp3rdClass extends AppCompatActivity {

    private static final String TAG = "SignUp3rdClass";

    ScrollView scrollView;
    CountryCodePicker countryCodePicker;
    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);
        countryCodePicker = findViewById(R.id.country_code_picker);
        phoneNumber = findViewById(R.id.signup_phone_number);

    }

    public void callVerifyOTPScreen(View view) {

        if(!validatePhoneNumber()){
            return;
        }

        try {

            Intent intentPrevious = getIntent();
            String fullName = intentPrevious.getStringExtra("fullName");
            String username = intentPrevious.getStringExtra("username");
            String email = intentPrevious.getStringExtra("email");
            String password = intentPrevious.getStringExtra("password");

            String gender = intentPrevious.getStringExtra("gender");
            String date = intentPrevious.getStringExtra("age");
            String getUserEnteredPhoneNumber = phoneNumber.getEditText().getText().toString().trim();
            String phoneNo = "+"+countryCodePicker.getSelectedCountryCode()+getUserEnteredPhoneNumber;//getFullNumber

            Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

            intent.putExtra("fullName",fullName);
            intent.putExtra("username",username);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            intent.putExtra("gender",gender);
            intent.putExtra("age",date);
            intent.putExtra("phoneNo",phoneNo);

            //todo Add Transition
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.i(TAG, "callVerifyOTPScreen: "+e.getLocalizedMessage());
        }

    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getEditText().getText().toString().trim();
        String checkspaces = "Aw{1,20}z";
        if (val.isEmpty()) {
            phoneNumber.setError("Enter valid phone number");
            return false;
        } else if (val.contains(" ")) {
            phoneNumber.setError("No White spaces are allowed!");
            return false;
        } else {
            phoneNumber.setError(null);
            phoneNumber.setErrorEnabled(false);
            return true;
        }
    }
}