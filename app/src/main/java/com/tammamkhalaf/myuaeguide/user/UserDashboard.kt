package com.tammamkhalaf.myuaeguide.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.common.api.internal.BackgroundDetector.initialize
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.nostra13.universalimageloader.core.ImageLoader
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.chat.ChatActivity
import com.tammamkhalaf.myuaeguide.common.loginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.common.loginSignup.login.java.PhoneAuthActivity
import com.tammamkhalaf.myuaeguide.databases.firebase.storage.Utility.UniversalImageLoader
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedHelperClass
import com.tammamkhalaf.myuaeguide.locationOwner.SettingsActivity
import com.tammamkhalaf.myuaeguide.viewmodels.UserDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.*
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
open class UserDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var featuredRecycler: RecyclerView? = null

    var mostViewedRecycler: RecyclerView? = null

    var categoriesRecycler: RecyclerView? = null
    var categoriesAdapter: CategoriesAdapter? = null

    var menuIcon: ImageView? = null
    var content: LinearLayout? = null

    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null

    lateinit var latitude:String
    lateinit var longitude:String

    private lateinit var viewModel: UserDashboardViewModel

    private var listOfMostViewedAdapter = ArrayList<MostViewedHelperClass>()
    private var listOfFeaturedAdapter = ArrayList<FeaturedHelperClass>()

    lateinit var container: ShimmerFrameLayout

    /**
     * permissions request code
     */
    private val REQUEST_CODE_ASK_PERMISSIONS = 1

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private val REQUIRED_SDK_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION)


    //Declaring the needed Variables
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel = ViewModelProvider(this).get(UserDashboardViewModel::class.java)

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler)
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler)
        categoriesRecycler = findViewById(R.id.categories_recycler)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        menuIcon = findViewById(R.id.menu_icon)
        content = findViewById(R.id.content)

        // Get a reference to the AutoCompleteTextView in the layout
        val textView = findViewById<AutoCompleteTextView>(R.id.autocomplete_suggest)
        // Get the string array
        val countries: Array<out String> = resources.getStringArray(R.array.searchDiscoverExploreSuggest)
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries).also { adapter ->
            textView.setAdapter(adapter)
        }

        checkPermissions()

        Observable.create { emitter: ObservableEmitter<Any?>? ->
            textView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    Log.d(TAG, "afterTextChanged: ${s.toString()}")

                    if (s?.length != 0)
                        emitter?.onNext(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.d(TAG, "beforeTextChanged: ${s.toString()}")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d(TAG, "beforeTextChanged: ${s.toString()}")
                }
            })

        }.doOnNext { c -> Log.d(TAG, "here > upstream: $c") }
                .map {
                    //if you need to apply any thing to
                    // object receive it before send it to final step
                    it.toString().trim()
                }.debounce(5, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .filter {
                    (it.toString() != "abc")
                    /*filtering some word based on text or size
                     or other thing you need to filter it here */
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { d ->
                    Log.d(TAG, "downstream :  --> CALLING here developer API $d")
                    var list = ArrayList<String>()
                    list.add(d.toString())
                    sendDataToApiDemo(list)
                }


        textView.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = v.text.toString()
                Toast.makeText(this@UserDashboard, text, Toast.LENGTH_SHORT).show()
                return@OnEditorActionListener true
            }
            false
        })

        //endregion

        //Functions will be executed automatically when this activity will be created
        navigationDrawer()

        categoriesRecycler()

        textView.afterTextChanged {
            /*doSomethingWithText(it)*/
            Log.d(TAG, "onCreate: afterTextChanged I am calling api")
        }


        initImageLoader()
    }

    /**
     * init universal image loader
     */
    private fun initImageLoader() {
        var imageLoader = UniversalImageLoader(this)
        ImageLoader.getInstance().init(imageLoader.config)
    }

    private fun sendDataToApiDemo(data: ArrayList<String>) {

        container.startShimmer()

        viewModel.discoverExplorePlacesHereDeveloper("dmLgAQo631UJfwF5R2hH", "391hkRjz5Z3Ee1h3wz6Kng",
                "$latitude,$longitude", data)

        viewModel.discoverExplorePlacesHereDeveloperLiveData.observe(this,
                Observer {
                    for (item in it.results.items) {
                        var str: StringBuilder
                        if (item.title.length > 21) {
                            str = java.lang.StringBuilder(item.title)
                            str.insert(21, "\n").toString()
                        } else {
                            str = java.lang.StringBuilder(item.title)
                        }
                        listOfMostViewedAdapter.add(MostViewedHelperClass(
                                item.icon,
                                str.toString(),
                                item?.alternativeNames?.get(0)?.name ?: "",
                                item.category.title ?: "Category?",
                                item.openingHours?.label ?: "Opening Hours",
                                item.openingHours?.text?.replace("<br/>", "\n")
                                        ?: "Not Available?",
                                rating = item.averageRating ?: 4.0,
                                id = item.id
                        ))
                    }

                    if (mostViewedRecycler?.adapter != null) // it works second time and later
                        mostViewedRecycler?.adapter?.notifyDataSetChanged()
                    else {
                        mostViewedRecycler?.adapter = MostViewedAdapter(listOfMostViewedAdapter,
                                this)
                    }
                    container.stopShimmer()
                    container.visibility = GONE
                    mostViewedRecycler?.visibility = View.VISIBLE
                })
    }

    abstract class NYTSubscriber<T> : Subscriber<T> {
        fun onCompleted() {}
        override fun onError(e: Throwable?) {
            Log.e(TAG, "onError: ", e)
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
                Log.d(TAG, "afterTextChanged: from extension function ${editable.toString()}")
            }
        })
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
        when (item.itemId) {
            R.id.nav_all_categories -> {
                startActivity(Intent(applicationContext, AllCategories::class.java))
            }
            R.id.nav_favorite -> {
                startActivity(Intent(applicationContext, FavoriteActivity::class.java))
            }
            R.id.nav_login -> {
                startActivity(Intent(applicationContext, PhoneAuthActivity::class.java))
            }
            R.id.nav_home -> {
                startActivity(Intent(applicationContext, UserDashboard::class.java))
            }
            R.id.nav_settings -> {
                startActivity(Intent(applicationContext, SettingsActivity::class.java))
            }
            R.id.nav_chatroom -> {
                startActivity(Intent(applicationContext, ChatActivity::class.java))
            }
            else -> {
                Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout!!.isDrawerVisible(GravityCompat.START)) {
            drawerLayout!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //endregion

    //region featured
    private fun featuredRecycler() {

        featuredRecycler?.setHasFixedSize(true)
        featuredRecycler?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        //todo we can add array of url images and downloaded with glide or picasso

        val container = findViewById<View>(R.id.shimmerFrameLayout) as ShimmerFrameLayout
        container.startShimmer()

        var list = ArrayList<String>()

        list.add("going-out")
        list.add("natural-geographical")
        list.add("leisure-outdoor")

        Log.d(TAG, ("featuredRecycler: " + this::latitude.isInitialized + this::longitude.isInitialized))
        Log.d(TAG, ("featuredRecycler: $latitude<-->$longitude"))

        if(this::latitude.isInitialized || this::longitude.isInitialized) {
            viewModel.discoverHerePlacesHereDeveloper("dmLgAQo631UJfwF5R2hH", "391hkRjz5Z3Ee1h3wz6Kng",
                    "$latitude,$longitude", list)
        }

        viewModel.discoverHerePlacesHereDeveloperLiveData.observe(this, Observer {
            for (item in it.results.items) {
                var str: StringBuilder
                if (item.title.length > 25) {
                    str = java.lang.StringBuilder(item.title)
                    str.insert(25, "\n").toString()
                } else {
                    str = java.lang.StringBuilder(item.title)
                }

                listOfFeaturedAdapter.add(FeaturedHelperClass(item.icon, str.toString(), item.category.title, item.id))
            }

            if (featuredRecycler?.adapter != null) {
                featuredRecycler?.adapter?.notifyDataSetChanged()
            } else {
                featuredRecycler?.adapter = FeaturedAdapter(listOfFeaturedAdapter, this)
            }

            container.stopShimmer()
            container.visibility = GONE
            featuredRecycler?.visibility = View.VISIBLE

        })

    }


    //endregion

    //region categories
    private fun categoriesRecycler() {
        //All Gradients
        val gradient2 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(R.color.card1, -R.color.card1))
        val gradient1 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(R.color.card2, -R.color.card2))
        val gradient3 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(R.color.card3, -R.color.card3))
        val gradient4 = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(R.color.card4, -R.color.card4))
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

        // app_id dmLgAQo631UJfwF5R2hH //app_code 391hkRjz5Z3Ee1h3wz6Kng

        container = findViewById<View>(R.id.shimmerFrameLayoutMostViewed) as ShimmerFrameLayout

        container.startShimmer()

        var list = ArrayList<String>()

        list.add("coffee-tea")
        list.add("restaurant")
        list.add("snacks-fast-food")

        Log.d(TAG, ("mostViewedRecycler: " + this::latitude.isInitialized + this::longitude.isInitialized))
        Log.d(TAG, ("mostViewedRecycler: $latitude<-->$longitude"))

        if(this::latitude.isInitialized || this::longitude.isInitialized) {
            print(this::latitude.isInitialized || this::longitude.isInitialized)

            viewModel.discoverExplorePlacesHereDeveloper("dmLgAQo631UJfwF5R2hH", "391hkRjz5Z3Ee1h3wz6Kng",
                    "$latitude,$longitude", list)
        }
        viewModel.discoverExplorePlacesHereDeveloperLiveData.observe(this, Observer {
            for (item in it.results.items.asReversed()) {
                var str: StringBuilder
                if (item.title.length > 25) {
                    str = java.lang.StringBuilder(item.title)
                    str.insert(25, "\n").toString()
                } else {
                    str = java.lang.StringBuilder(item.title)
                }
                listOfMostViewedAdapter.clear()
                listOfMostViewedAdapter.add(MostViewedHelperClass(item.icon, str.toString(), item?.alternativeNames?.get(0)?.name
                        ?: "",
                        item.category.title ?: "Category?", item.openingHours?.label
                        ?: "Opening Hours",
                        item.openingHours?.text?.replace("<br/>", "\n") ?: "Not Available?",
                        rating = item.averageRating ?: 4.0
                        ,item.id
                ))
                Log.d(TAG, "context = ${it.search.context.href}")
            }
            if (mostViewedRecycler?.adapter != null) {
                mostViewedRecycler?.adapter?.notifyDataSetChanged()
            } else {
                mostViewedRecycler?.adapter = MostViewedAdapter(listOfMostViewedAdapter, this)
            }
            container.stopShimmer()
            container.visibility = GONE
            mostViewedRecycler?.visibility = View.VISIBLE
        })

    }

    private fun getMostImageUrl(xid: String): String {
        return "https://upload.wikimedia.org/wikipedia/commons/5/5b/Atlantis_The_Palm_hotel_from_the_sea%2C_Palm_Jumeirah%2C_Dubai.jpg"
    }


    //endregion

    //region request permission

    /**
     * Checks the dynamically-controlled permissions and requests missing permissions from end user.
     */
    private fun checkPermissions(){
        val missingPermissions: MutableList<String> = ArrayList()
        // check all required dynamic permissions
        for (permission in REQUIRED_SDK_PERMISSIONS) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }
        if (missingPermissions.isNotEmpty()) {
            // request all missing permissions
            val permissions = missingPermissions.toTypedArray()
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS)
        } else {
            val grantResults = IntArray(REQUIRED_SDK_PERMISSIONS.size)
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> {
                var index = permissions.size - 1
                while (index >= 0) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show()
                        finish()
                        return
                    }
                    --index
                }
                // all permissions were granted

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    // Got last known location. In some rare situations this can be null.
                    latitude = it.latitude.toString()
                    longitude = it.longitude.toString()
                    Log.d(TAG, "onRequestPermissionsResult: latitude = " + it.latitude)
                    Log.d(TAG, "onRequestPermissionsResult: longitude = " + it.longitude)

                    if(this::latitude.isInitialized || this::longitude.isInitialized) {
                        featuredRecycler()
                        mostViewedRecycler()
                    }
                }

                initialize(application)
            }

            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

        }
        Log.d(TAG, "onRequestPermissionsResult: " + " all permissions were granted")



    }

    //endregion




    companion object {
        private const val TAG = "UserDashboard"
        const val END_SCALE = 0.7f
    }

    fun callRetailerScreen(view: View) {
        startActivity(Intent(this, RetailerStartUpScreen::class.java))
    }

}