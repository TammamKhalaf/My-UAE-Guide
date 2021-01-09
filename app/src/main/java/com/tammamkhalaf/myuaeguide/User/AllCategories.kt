package com.tammamkhalaf.myuaeguide.User

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.User.AllCategories

class AllCategories : AppCompatActivity() {
    lateinit var back_btn: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_all_categories)
        back_btn = findViewById(R.id.back_pressed)
        back_btn.setOnClickListener {
            super@AllCategories.onBackPressed()
        }
    }
}