package com.tammamkhalaf.myuaeguide.locationOwner

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.tammamkhalaf.myuaeguide.R


class RetailerDashboard : AppCompatActivity() {

    var chipNavigationBar: ChipNavigationBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_retailer_dashboard)



        chipNavigationBar = findViewById(R.id.bottom_nav_menu)
        chipNavigationBar?.setItemSelected(R.id.bottom_nav_profile, true)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RetailerProfileFragment()).commit()
        bottomMenu()
    }

    private fun bottomMenu() {
        chipNavigationBar?.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(i: Int) {
                var fragment: Fragment? = null
                when (i) {
                    R.id.bottom_nav_dashboard -> fragment = RetailerDashboardFragment()
                    R.id.bottom_nav_manage -> fragment = RetailerManageFragment()
                    R.id.bottom_nav_profile -> fragment = RetailerProfileFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment!!).commit()
            }
        })
    }

    companion object {
        private const val TAG = "RetailerDashboard"
    }

}
