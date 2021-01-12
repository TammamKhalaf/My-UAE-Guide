package com.tammamkhalaf.myuaeguide.user

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.R.string
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceBuilder
import com.tammamkhalaf.myuaeguide.common.loginSignup.Login
import com.tammamkhalaf.myuaeguide.common.loginSignup.RetailerStartUpScreen
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.categories.CategoriesHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedAdapter
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.mostViewed.MostViewedHelperClass
import com.tammamkhalaf.myuaeguide.viewmodels.UserDashboardViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var featuredRecycler: RecyclerView? = null
    var featuredAdapter:  FeaturedAdapter? = null

    var mostViewedAdapter: MostViewedAdapter?=null
    var mostViewedRecycler: RecyclerView? = null

    var categoriesRecycler: RecyclerView? = null
    var categoriesAdapter: CategoriesAdapter? =null

    var menuIcon: ImageView? = null
    var content: LinearLayout? = null

    var drawerLayout: DrawerLayout? = null
    var navigationView: NavigationView? = null

    private lateinit var viewModel: UserDashboardViewModel

    lateinit var searchUserDashboardTextInputLayout: TextInputLayout
    lateinit var searchUserDashboardTextInputEditText: TextInputEditText

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
        searchUserDashboardTextInputLayout=findViewById(R.id.searchUserDashboardTextInputLayout)

        searchUserDashboardTextInputEditText=findViewById(R.id.searchUserDashboardTextInputEditText)

        searchUserDashboardTextInputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged: ${s.toString()}")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged: ${s.toString()}")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "beforeTextChanged: ${s.toString()}")
            }
        })

        navigationDrawer()
        //Functions will be executed automatically when this activity will be created
        featuredRecycler()
        mostViewedRecycler()
        categoriesRecycler()

        searchUserDashboardTextInputEditText.afterTextChanged {
        /*doSomethingWithText(it)*/
            Log.d(TAG, "onCreate: afterTextChanged I am calling api")
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
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
                startActivity(Intent(applicationContext, FavActivity::class.java))
            }
            R.id.nav_login -> {
                startActivity(Intent(applicationContext, Login::class.java))
            }
            R.id.nav_home -> {
                startActivity(Intent(applicationContext, UserDashboard::class.java))
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
        } else super.onBackPressed()
    }

    //endregion

    //region featured
    private fun featuredRecycler() {
        featuredRecycler?.setHasFixedSize(true)
        featuredRecycler?.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
      //todo we can add array of url images and downloaded with glide or picasso

        viewModel.getAllPlaceInBBox("en", -55.296249, 55.296249, -25.276987, 25.276987,
                "osm", "osm", "malls", 1, "json", 10,
                "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")

        viewModel.allPlacesInBBoxLiveData.observe(this, {
            var list = ArrayList<FeaturedHelperClass>()

            for (simpleFeature in it) {
                list?.add(FeaturedHelperClass(getImageUrl(simpleFeature.xid), simpleFeature.name, simpleFeature.xid))
            }
            featuredRecycler?.adapter = FeaturedAdapter(list,this)
        })

    }

    private fun getImageUrl(xid: String):String{
        var imageUrl:String = "https://upload.wikimedia.org/wikipedia/commons/5/56/Dining_at_Dubai_Festival_City_Mall.jpg"
        var oldUrl:String = "https://upload.wikimedia.org/wikipedia/commons/"
        var newUrl:String = "https://upload.wikimedia.org/wikipedia/commons/"

//        viewModel.getDetailedInfoAboutPlace("en", xid, "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")
//
//        viewModel.detailedInfoAboutPlaceLiveData.observe(this, {
//            oldUrl = it.image
//
//            val uri: Uri = Uri.parse(oldUrl)
//            val path: String? = uri.path
//
//            newUrl += path?.removePrefix("/wiki/File:")
//
//            Log.d(TAG, "getImageUrl: $newUrl ---")
//
//        })

        return imageUrl
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

        viewModel.getAllPlaceInBBox("en", -55.296249, 55.296249, -25.276987, 25.276987,
                "osm", "osm", "other_hotels", 1, "json", 10,
                "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")

        viewModel.allPlacesInBBoxLiveData.observe(this, {
            var list = ArrayList<MostViewedHelperClass>()

            for (simpleFeature in it) {
                list?.add(MostViewedHelperClass(getMostImageUrl(simpleFeature.xid), simpleFeature.name, simpleFeature.rate.toString()))
            }
            mostViewedRecycler?.adapter = MostViewedAdapter(list,this)
        })
    }

    private fun getMostImageUrl(xid: String):String{
        var imageUrl:String = "https://upload.wikimedia.org/wikipedia/commons/5/5b/Atlantis_The_Palm_hotel_from_the_sea%2C_Palm_Jumeirah%2C_Dubai.jpg"
        var oldUrl:String = "https://upload.wikimedia.org/wikipedia/commons/"
        var newUrl:String = "https://upload.wikimedia.org/wikipedia/commons/"

        //todo use debounce with this RxJava Call to reduce huge calls at a time
//        viewModel.getDetailedInfoAboutPlace("en", xid, "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed")
//
//        viewModel.detailedInfoAboutPlaceLiveData.observe(this, {
//            oldUrl = it.image
//
//            val uri: Uri = Uri.parse(oldUrl)
//            val path: String? = uri.path
//
//            newUrl += path?.removePrefix("/wiki/File:")
//
//            Log.d(TAG, "getImageUrl: $newUrl ---")
//
//        })

        return imageUrl
    }


    //endregion

    companion object {
        private const val TAG = "UserDashboard"
        const val END_SCALE = 0.7f
    }

    fun callRetailerScreen(view: View) {startActivity(Intent(applicationContext, RetailerStartUpScreen::class.java))}

}