package com.tammamkhalaf.myuaeguide.User

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.tammamkhalaf.myuaeguide.Categories.Hotels.GetHotelPhotosRegionTwo.HotelImage
import com.tammamkhalaf.myuaeguide.Categories.Hotels.HotelService
import com.tammamkhalaf.myuaeguide.Categories.Hotels.HotelServiceBuilder
import com.tammamkhalaf.myuaeguide.Categories.Hotels.searchHotelRegionOne.Properties
import com.tammamkhalaf.myuaeguide.Categories.Hotels.searchHotelRegionOne.Suggestion
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Place
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Results
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.ServiceBuilder
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.TrueWayPlacesService
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesAdapter
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesHelperClass
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedAdapter
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedAdapter
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedHelperClass
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.User.UserDashboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
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
    var call: Call<Results?>? = null
    var places: ArrayList<Place?>? = null
    var suggestions: ArrayList<Suggestion?>? = null

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
        val service = ServiceBuilder.buildService(TrueWayPlacesService::class.java)
        val images = intArrayOf(R.drawable.coffee_shop)
        // todo add array of images

        // todo 429 Too Many Requests
        //  todo use Rxjava to separate time of consuming api
        val TrueWayPlacesFilterMap = HashMap<String, String>()
        val locationCoordinates = "24.199168,55.719391"
        TrueWayPlacesFilterMap["location"] = locationCoordinates
        /**
         * type >>>>
         * airport,amusement_park,aquarium,art_gallery,atm,
         * bakery,bank,bar,beauty_salon,bicycle_store,book_store,bowling,bus_station
         * cafe,campground,car_dealer,car_rental,car_repair,car_wash,casino,cemetery,church,cinema,city_hall,clothing_store,convenience_store,courthouse
         * dentist,department_store,doctor,electrician,electronics_store,embassy
         * fire_station,flowers_store,funeral_service,furniture_store,gas_station
         * government_office,grocery_store,gym,
         * hairdressing_salon,hardware_store,homegoodsstore,hospital,insurance_agency,jewelry_store
         * laundry,lawyer,library,liquor_store,locksmith,lodging,mosque,museum,night_club
         * park,parking,pet_store,pharmacy,plumber,police_station,post_office,primary_school,
         * rail_station,realestateagency,restaurant,rv_park
         * school,secondary_school,shoe_store,shopping_center,spa,stadium,storage,store,subway_station,supermarket,synagogue
         * taxi_stand,temple,tourist_attraction,train_station,transit_station,travel_agency,university
         * veterinarian,zoo
         */
        TrueWayPlacesFilterMap["type"] = "cafe"
        call = service!!.findPlacesNearby(TrueWayPlacesFilterMap, 10000, "en")
        call!!.enqueue(object : Callback<Results> {
            override fun onResponse(call: Call<Results>, response: Response<Results>) {
                if (response.isSuccessful) {
                    places = response.body().getResults() as ArrayList<Place?>
                    for (place in places!!) {
                        featuredLocations.add(FeaturedHelperClass(images[0], place.getName(), place.getPhoneNumber()))
                    }
                    adapter = FeaturedAdapter(featuredLocations, this@UserDashboard)
                    featuredRecycler!!.adapter = adapter
                } else if (response.code() == 401) {
                    Toast.makeText(this@UserDashboard, string.SessionExpired, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Results>, t: Throwable) {
                if (t is IOException) {
                    Toast.makeText(this@UserDashboard, string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
                }
            }
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
        adapter = CategoriesAdapter(categoriesHelperClasses)
        categoriesRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecycler!!.adapter = adapter
    }

    //endregion
    //region mostViewed recycler view
    private fun mostViewedRecycler() {
        mostViewedRecycler!!.setHasFixedSize(true)
        mostViewedRecycler!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val service = HotelServiceBuilder.buildService(HotelService::class.java)
        val mostViewedLocations = ArrayList<MostViewedHelperClass>()
        //todo use hotel search take id of an item then image ->> property detail
        val imagesFromApi = ArrayList<List<HotelImage>>()
        val call = service!!.searchHotel("dubai", "en_US")
        call!!.enqueue(object : Callback<Properties> {
            override fun onResponse(call: Call<Properties>, response: Response<Properties>) {
                if (response.isSuccessful) {
                    suggestions = response.body()!!.suggestions as ArrayList<Suggestion?>
                    Log.d(TAG, "onResponse: " + response.body()!!.trackingID)
                    for (suggestion in suggestions!!) {
                        for (i in suggestion!!.entities!!.indices) {
                            mostViewedLocations.add(MostViewedHelperClass(R.drawable.beds,
                                    suggestion.entities!![i]!!.name,
                                    suggestion.entities!![i]!!.destinationId))
                        }
                    }
                    adapter = MostViewedAdapter(mostViewedLocations)
                    mostViewedRecycler!!.adapter = adapter
                } else if (response.code() == 401) {
                    Toast.makeText(this@UserDashboard, string.SessionExpired, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "onResponse: Not-Successful Response Code = " + response.code() +
                            "-->>> Response Error Body:" + response.errorBody().toString())
                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Properties>, t: Throwable) {
                if (t is IOException) {
                    Toast.makeText(this@UserDashboard, string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage())
                } else {
                    Log.d(TAG, "onFailure: " + t.localizedMessage)
                    Toast.makeText(this@UserDashboard, string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show()
                }
            }
        })
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