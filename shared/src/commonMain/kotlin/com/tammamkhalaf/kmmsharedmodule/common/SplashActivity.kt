package com.tammamkhalaf.myuaeguide.common

import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    var onBoardingScreen: android.content.SharedPreferences? = null
    var backgroundIV: android.widget.ImageView? = null
    var powered_by_line: android.widget.TextView? = null
    var sideAnim: android.view.animation.Animation? = null
    var bottomAnim: android.view.animation.Animation? = null
    protected fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)
        backgroundIV = findViewById(R.id.background_image)
        powered_by_line = findViewById(R.id.powered_by_line)
        sideAnim = android.view.animation.AnimationUtils.loadAnimation(this@SplashActivity, R.anim.side_anim)
        bottomAnim = android.view.animation.AnimationUtils.loadAnimation(this@SplashActivity, R.anim.bottom_anim)
        backgroundIV.setAnimation(sideAnim)
        powered_by_line.setAnimation(bottomAnim)
        val SPLASH_TIMER = 5000
        android.os.Handler().postDelayed(java.lang.Runnable {
            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
            val isFirstTime: Boolean = onBoardingScreen.getBoolean("firstTime", true)
            if (isFirstTime) {
                val editor: android.content.SharedPreferences.Editor = onBoardingScreen.edit()
                editor.putBoolean("firstTime", false)
                editor.commit()
                val intent: android.content.Intent = android.content.Intent(getApplicationContext(), OnBoarding::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent: android.content.Intent = android.content.Intent(getApplicationContext(), UserDashboard::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_TIMER.toLong())
    }
}