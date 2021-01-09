package com.tammamkhalaf.myuaeguide.LocationOwner

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.tammamkhalaf.myuaeguide.R
import java.util.*

class RetailerDashboard : AppCompatActivity() {
    var chipNavigationBar: ChipNavigationBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_retailer_dashboard)
        chipNavigationBar = findViewById(R.id.bottom_nav_menu)
        chipNavigationBar?.run { setItemSelected(R.id.bottom_nav_dashboard, true) }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RetailerDashboardFragment()).commit()
        bottomMenu()
    }

    private fun bottomMenu() {
        chipNavigationBar?.setOnClickListener {
            var fragment: Fragment? = null
            when (it.id) {
                R.id.bottom_nav_dashboard -> fragment = RetailerDashboardFragment()
                R.id.bottom_nav_manage -> fragment = RetailerManageFragment()
                R.id.bottom_nav_profile -> fragment = RetailerProfileFragment()
            }
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
        }
    }

}
