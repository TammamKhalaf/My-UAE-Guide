package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tammamkhalaf.myuaeguide.R;

import java.util.Calendar;

public class SignUp2ndClass extends AppCompatActivity {
    private static final String TAG = "SignUp2ndClass";

    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    int gender;
    int currentAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2nd_class);

        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);
        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);

    }

    public void call3rdSigupScreen(View view) {

        if(!validateAge() | !validateGender()){
            finish();
        }

            Intent intentPrevious = getIntent();
            String fullname = intentPrevious.getStringExtra("fullName");
            String username = intentPrevious.getStringExtra("username");
            String email = intentPrevious.getStringExtra("email");
            String password = intentPrevious.getStringExtra("password");

            selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());

            String selectedGend = selectedGender.getText().toString();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            String DOB = day+"/"+month+"/"+year;

        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);



            intent.putExtra("fullName", fullname);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            intent.putExtra("password",password);
            intent.putExtra("age",DOB);
            intent.putExtra("gender",selectedGend);

            //Add Transition and call next activity
            Pair[] pairs = new Pair[5];
            pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
            pairs[1] = new Pair(next, "transition_next_btn");
            pairs[2] = new Pair(login, "transition_login_btn");
            pairs[3] = new Pair(titleText, "transition_title_text");
            pairs[4] = new Pair(slideText, "transition_slide_text");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }


    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            gender = radioGroup.getCheckedRadioButtonId();
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = 2021 - userAge;

        android.util.Log.i(TAG, "validateAge: currentYear Is = "+currentYear);

        if (isAgeValid < 14) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            currentAge = isAgeValid;
            return true;
    }


}