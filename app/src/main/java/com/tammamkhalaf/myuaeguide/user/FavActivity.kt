package com.tammamkhalaf.myuaeguide.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.viewmodels.UserDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

class FavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_fav)
    }

}