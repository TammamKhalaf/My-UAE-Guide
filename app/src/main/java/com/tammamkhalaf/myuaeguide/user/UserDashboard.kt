package com.tammamkhalaf.myuaeguide.user

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceBuilder
import com.tammamkhalaf.myuaeguide.common.loginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedHelperClass
import com.tammamkhalaf.myuaeguide.viewmodels.UserDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class UserDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var featuredRecycler: RecyclerView? = null
    var featuredAdapter:FeaturedAdapter? = null
    var mostViewedRecycler: RecyclerView? = null
    var categoriesRecycler: RecyclerView? = null
    var categoriesAdapter: CategoriesAdapter? =null
    var mostViewedAdapter: MostViewedAdapter?=null
    var menuIcon: ImageView? = null
    var content: LinearLayout? = null
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    private lateinit var viewModel: UserDashboardViewModel

    //todo add section for the mahrajanat in uae mahrajan zayed etc ....
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_user_dashboard)

        viewModel = ViewModelProvider(this).get(UserDashboardViewModel::class.java)

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler)
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler)
        categoriesRecycler = findViewById(R.id.categories_recycler)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        menuIcon = findViewById(R.id.menu_icon)
        content = findViewById(R.id.content)

        navigationDrawer()
        //Functions will be executed automatically when this activity will be created
        featuredRecycler()
        mostViewedRecycler()
        categoriesRecycler()
    }

    //region navigation drawer
    private fun navigationDrawer() {
        navigationView!!.bringToFront()
        navigationView!!.setNavigationItemSelectedListener(this)
        navigationView!!.setCheckedItem(R.id.nav_home)
        menuIcon!!.setOnClickListener {
            if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) {
                drawerLayout!!.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout!!.openDrawer(GravityCompat.START)
            }
        }
        animateNavigationDrawer()
    }

    private fun animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout!!.setScrimColor(resources.getColor(R.color.banner_background_light)) //Color.TRANSPARENT
        drawerLayout!!.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offsetScale = 1 - diffScaledOffset
                content!!.scaleX = offsetScale
                content!!.scaleY = offsetScale

                // Translate the View, accounting for the scaled width
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff = content!!.width * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                content!!.translationX = xTranslation
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_all_categories) {
            startActivity(Intent(applicationContext, AllCategories::class.java))
        } else {
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    //endregion

    //region featured
    private fun featuredRecycler() {
        featuredRecycler?.setHasFixedSize(true)
        featuredRecycler?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
      //todo we can add array of url images and downloaded with glide or picasso

//        viewModel.getDetailedInfoAboutPlace("en","W286786280","5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")
//
//        viewModel.detailedInfoAboutPlaceLiveData.observe(this, {
//            featuredAdapter?.featuredLocations?.add(FeaturedHelperClass(it.image, it.name, it.wikidata))
//        })
        viewModel.getAllPlaceInBBox("en",-55.296249,55.296249,-25.276987,25.276987,"osm","osm","malls"
        ,1,"json",10,"5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")

        viewModel.allPlacesInBBoxLiveData.observe(this, {
            var list: ArrayList<FeaturedHelperClass> = ArrayList()

            for (simpleFeature in it) {
                Log.d(TAG, "featuredRecycler: List=${simpleFeature.name}")
                list?.add(FeaturedHelperClass("", simpleFeature.name, simpleFeature.xid))
            }



            featuredRecycler?.adapter = list?.let { it1 -> FeaturedAdapter(it1, this) }
        })


    }


    //endregion

    //region categories
    private fun categoriesRecycler() {
        //All Gradients
        val gradient2 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(-0x2b341b, -0x2b341b))
        val gradient1 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(-0x852331, -0x852331))
        val gradient3 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(-0x83a61, -0x83a61))
        val gradient4 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(-0x47280b, -0x47280b))
        val categoriesHelperClasses = ArrayList<CategoriesHelperClass>()
        categoriesHelperClasses.add(CategoriesHelperClass(gradient1, R.drawable.school_cat, getString(string.education)))
        categoriesHelperClasses.add(CategoriesHelperClass(gradient2, R.drawable.hospital_cat, getString(string.hospital)))
        categoriesHelperClasses.add(CategoriesHelperClass(gradient3, R.drawable.res_cat, getString(string.restaurant)))
        categoriesHelperClasses.add(CategoriesHelperClass(gradient4, R.drawable.shopping_cat, getString(string.shopping)))
        categoriesHelperClasses.add(CategoriesHelperClass(gradient1, R.drawable.transport_cat, getString(string.transport)))
        categoriesRecycler!!.setHasFixedSize(true)
        categoriesAdapter = CategoriesAdapter(categoriesHelperClasses)
        categoriesRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecycler!!.adapter = categoriesAdapter
    }

    //endregion

    //region mostViewed recycler view
    private fun mostViewedRecycler() {
        mostViewedRecycler!!.setHasFixedSize(true)
        mostViewedRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val service = HotelServiceBuilder.buildService(HotelServiceApi::class.java)
        val mostViewedLocations = ArrayList<MostViewedHelperClass>()
        //todo use hotel search take id of an item then image ->> property detail

    }


    //endregion

    fun callRetailerScreen() {
        startActivity(Intent(applicationContext, RetailerStartUpScreen::class.java))
    }

    companion object {
        private const val TAG = "UserDashboard"
        const val END_SCALE = 0.7f
    }

}