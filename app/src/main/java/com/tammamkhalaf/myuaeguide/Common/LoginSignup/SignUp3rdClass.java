package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tammamkhalaf.myuaeguide.R;

public class SignUp3rdClass extends AppCompatActivity {

    ScrollView scrollView;

    TextInputLayout phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up3rd_class);

        scrollView = findViewById(R.id.signup_3rd_screen_scroll_view);


        Intent intentPrevious = getIntent();
        String fullname = intentPrevious.getStringExtra("fullName");
        String username = intentPrevious.getStringExtra("username");
        String email = intentPrevious.getStringExtra("email");
        String password = intentPrevious.getStringExtra("password");

        String gender = intentPrevious.getStringExtra("gender");
        String currentAge = intentPrevious.getStringExtra("age");

        Toast.makeText(getApplicationContext(), "fullName:"+fullname+"\n"+"username:"+username
                +"\n"+"email:"+email+"\n"+"password:"+password+"\n"+"age"+currentAge+
                "\n"+"gender:"+gender, Toast.LENGTH_LONG).show();
    }

    public void callVerifyOTPScreen(View view) {

        if(!validatePhoneNumber()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);

        //Add Transition
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(scrollView, "transition_OTP_screen");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp3rdClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
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