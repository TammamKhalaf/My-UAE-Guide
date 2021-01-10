package com.tammamkhalaf.myuaeguide.common.LoginSignup.ForgetPassword

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.R

class ForgetPasswordSuccessMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_forget_password_success_message)
    }

    fun backToLogin(view: View?) {}
}