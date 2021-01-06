package com.tammamkhalaf.myuaeguide.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.tammamkhalaf.myuaeguide.R;

public class AllCategories extends AppCompatActivity {

    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_categories);

        back_btn = findViewById(R.id.back_pressed);

        back_btn.setOnClickListener(view -> {
            AllCategories.super.onBackPressed();
        });

    }
}