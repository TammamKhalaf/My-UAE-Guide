package com.tammamkhalaf.kmmsharedmodule.common.loginSignup

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.common.loginSignup.signUp.SignUp
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity

class RetailerStartUpScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_retailer_start_up_screen)
    }

    fun callLoginScreen(view: View?) {
        val intent = Intent(applicationContext, PhoneAuthActivity::class.java)
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
        pairs[0] = Pair<View, String>(findViewById(R.id.login_btn), "transition_login")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@RetailerStartUpScreen, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

    fun callSignUpScreen(view: View?) {
        val intent = Intent(applicationContext, SignUp::class.java)
        val pairs: Array<Pair<View, String>?> = arrayOfNulls(1)
        pairs[0] = Pair<View, String>(findViewById(R.id.signup_btn), "transition_signup")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions.makeSceneTransitionAnimation(this@RetailerStartUpScreen, *pairs)
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }
}