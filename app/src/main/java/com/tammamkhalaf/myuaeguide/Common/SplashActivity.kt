package com.tammamkhalaf.myuaeguide.Common

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.User.UserDashboard

class SplashActivity : AppCompatActivity() {
    var onBoardingScreen: SharedPreferences? = null
    var backgroundIV: ImageView? = null
    var powered_by_line: TextView? = null
    var sideAnim: Animation? = null
    var bottomAnim: Animation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)
        backgroundIV = findViewById(R.id.background_image)
        powered_by_line = findViewById(R.id.powered_by_line)
        sideAnim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.side_anim)
        bottomAnim = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.bottom_anim)
        backgroundIV.setAnimation(sideAnim)
        powered_by_line.setAnimation(bottomAnim)
        val SPLASH_TIMER = 5000
        Handler().postDelayed({
            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
            val isFirstTime = onBoardingScreen.getBoolean("firstTime", true)
            if (isFirstTime) {
                val editor = onBoardingScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                val intent = Intent(applicationContext, OnBoarding::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(applicationContext, UserDashboard::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIMER.toLong())
    }
}