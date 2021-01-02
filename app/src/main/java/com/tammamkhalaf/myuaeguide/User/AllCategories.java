package com.tammamkhalaf.myuaeguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tammamkhalaf.myuaeguide.R;

public class AllCategories extends AppCompatActivity {

    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        back_btn = findViewById(R.id.back_pressed);

        back_btn.setOnClickListener(view -> {
            AllCategories.super.onBackPressed();
        });

    }
}