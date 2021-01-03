package com.tammamkhalaf.myuaeguide.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tammamkhalaf.myuaeguide.R;

public class SetNewPassword extends AppCompatActivity {

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_new_password);


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
    }
}