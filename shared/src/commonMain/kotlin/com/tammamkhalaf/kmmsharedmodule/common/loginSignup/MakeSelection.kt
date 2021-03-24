package com.tammamkhalaf.kmmsharedmodule.common.loginSignup

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.R

class MakeSelection : AppCompatActivity() {
    var animation: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_make_selection)


        //Animation Hook
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_animation)

        //Set animation to all the elements
//        screenIcon.setAnimation(animation);
//        title.setAnimation(animation);
//        description.setAnimation(animation);
//        phoneNumberTextField.setAnimation(animation);
//        countryCodePicker.setAnimation(animation);
//        nextBtn.setAnimation(animation);
    }

    fun callBackScreenFromMakeSelection(view: View?) {}
    fun callOTPScreenFromMakeSelection(view: View?) {}
}