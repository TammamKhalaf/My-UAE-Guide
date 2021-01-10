package com.tammamkhalaf.myuaeguide.user

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.tammamkhalaf.myuaeguide.categories.hotels.getHotelPhotosModel.HotelImage
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceBuilder
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.ServiceBuilder
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi
import com.tammamkhalaf.myuaeguide.common.LoginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Categories.CategoriesAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Categories.CategoriesHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.MostViewed.MostViewedHelperClass
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.viewmodels.UserDashboardViewModel
import java.util.*

class UserDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var featuredRecycler: RecyclerView? = null
    var mostViewedRecycler: RecyclerView? = null
    var categoriesRecycler: RecyclerView? = null
    var adapter: RecyclerView.Adapter<*>? = null
    var menuIcon: ImageView? = null
    var content: LinearLayout? = null
    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null
    lateinit var viewModel: UserDashboardViewModel

    //todo add section for the mahrajanat in uae mahrajan zayed etc ....
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_user_dashboard)

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler)
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler)
        categoriesRecycler = findViewById(R.id.categories_recycler)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        menuIcon = findViewById(R.id.menu_icon)
        content = findViewById(R.id.content)

        //Functions will be executed automatically when this activity will be created
        featuredRecycler()
        mostViewedRecycler()
        categoriesRecycler()
        navigationDrawer()
        
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
        featuredRecycler!!.setHasFixedSize(true)
        featuredRecycler!!.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val featuredLocations = ArrayList<FeaturedHelperClass>()
        val service = ServiceBuilder.buildService(TrueWayPlacesServiceApi::class.java)
        val images = intArrayOf(R.drawable.coffee_shop)

//                if (response.isSuccessful) {
//                    places = response.body()?.results as ArrayList<Place?>
//                    for (place in places!!) {
//                        if (place != null) {
//                            featuredLocations.add(FeaturedHelperClass(images[0], place.name, place.phoneNumber))
//                        }
//                    }
//                    adapter = FeaturedAdapter(featuredLocations, this@UserDashboard)
//                    featuredRecycler!!.adapter = adapter
//                } else if (response.code() == 401) {
//                    Toast.makeText(this@UserDashboard, string.SessionExpired, Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
//                }
//    }
//                if (t is IOException) {
//                    Toast.makeText(this@UserDashboard, string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
//                }

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
        adapter = CategoriesAdapter(categoriesHelperClasses)
        categoriesRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecycler!!.adapter = adapter
    }

    //endregion

    //region mostViewed recycler view
    private fun mostViewedRecycler() {
        mostViewedRecycler!!.setHasFixedSize(true)
        mostViewedRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val service = HotelServiceBuilder.buildService(HotelServiceApi::class.java)
        val mostViewedLocations = ArrayList<MostViewedHelperClass>()
        //todo use hotel search take id of an item then image ->> property detail
        val imagesFromApi = ArrayList<List<HotelImage>>()
        CallingSearchHotelApi(service, mostViewedLocations)
    }

    private fun CallingSearchHotelApi(serviceApi: HotelServiceApi, mostViewedLocations: ArrayList<MostViewedHelperClass>) {
        val call = serviceApi!!.searchHotel("dubai", "en_US")
 //       call!!.enqueue(object : Callback<HotelResponse?> {
//            override fun onResponse(call: Call<HotelResponse?>, response: Response<HotelResponse?>) {
//                if (response.isSuccessful) {
//                    suggestions = response.body()!!.suggestions as ArrayList<Suggestion?>
//                    Log.d(TAG, "onResponse: " + response.body()!!.trackingID)
//                    for (suggestion in suggestions!!) {
//                        for (i in suggestion!!.entities!!.indices) {
//                            mostViewedLocations.add(MostViewedHelperClass(R.drawable.beds,
//                                    suggestion.entities!![i]!!.name,
//                                    suggestion.entities!![i]!!.destinationId))
//                        }
//                    }
//                    adapter = MostViewedAdapter(mostViewedLocations)
//                    mostViewedRecycler!!.adapter = adapter
//                } else if (response.code() == 401) {
//                    Toast.makeText(this@UserDashboard, string.SessionExpired, Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.d(TAG, "onResponse: Not-Successful Response Code = " + response.code() +
//                            "-->>> Response Error Body:" + response.errorBody().toString())
//                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
//                }
//            }

//            override fun onFailure(call: Call<HotelResponse?>, t: Throwable) {
//                if (t is IOException) {
//                    Toast.makeText(this@UserDashboard, string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage())
//                } else {
//                    Log.d(TAG, "onFailure: " + t.localizedMessage)
//                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
//                }
 //           }
    //       })
    }

    //endregion

    fun callRetailerScreen(view: View?) {
        startActivity(Intent(applicationContext, RetailerStartUpScreen::class.java))
    }

    companion object {
        private const val TAG = "UserDashboard"
        const val END_SCALE = 0.7f
    }
}