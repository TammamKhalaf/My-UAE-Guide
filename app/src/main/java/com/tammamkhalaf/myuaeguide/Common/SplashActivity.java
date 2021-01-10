package com.tammamkhalaf.myuaeguide.Common;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.User.UserDashboard;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences onBoardingScreen;

    ImageView backgroundIV;
    TextView powered_by_line;

    Animation sideAnim,bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        backgroundIV=findViewById(R.id.background_image);
        powered_by_line=findViewById(R.id.powered_by_line);


        sideAnim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.bottom_anim);

        backgroundIV.setAnimation(sideAnim);
        powered_by_line.setAnimation(bottomAnim);

        int SPLASH_TIMER = 5000;
        new Handler().postDelayed(() -> {

            onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
            boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

            if(isFirstTime){
                SharedPreferences.Editor editor =onBoardingScreen.edit();
                editor.putBoolean("firstTime",false);
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), OnBoarding.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intent);
                finish();
            }


        }, SPLASH_TIMER);

    }
}