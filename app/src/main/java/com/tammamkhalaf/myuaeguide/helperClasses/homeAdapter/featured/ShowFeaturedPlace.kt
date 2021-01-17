package com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.tammamkhalaf.myuaeguide.R
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse
import com.tammamkhalaf.myuaeguide.databases.hereDeveloper.AppDatabase
import com.tammamkhalaf.myuaeguide.databinding.ActivityShowFeaturedPlaceBinding
import com.tammamkhalaf.myuaeguide.viewmodels.ShowFeaturedPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.internal.util.HalfSerializer.onComplete
import io.reactivex.internal.util.HalfSerializer.onError
import io.reactivex.plugins.RxJavaPlugins.onSubscribe
import io.reactivex.schedulers.Schedulers


@AndroidEntryPoint
class ShowFeaturedPlace : AppCompatActivity() {
    //This works if you have used a variable in your <data> tag and you have built your project afterwards, if you don't have an activity
    var binding: ActivityShowFeaturedPlaceBinding? = null
    private lateinit var viewModel: ShowFeaturedPlaceViewModel
    lateinit var toolBarLayout: CollapsingToolbarLayout
    var id: String? = null
    private lateinit var imageFromApi: ImageView
    private lateinit var url: String

    private var placeResponse: PlaceResponse? = null

    //if you have an activity, you can use setContentView from the DataBindingUtils. Don't forget to delete the generic setContentView
    //ActivityShowFeaturedPlaceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_featured_place);
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityShowFeaturedPlaceBinding.inflate(layoutInflater)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ShowFeaturedPlaceViewModel::class.java)

        setContentView(binding!!.root)
        val toolbar = binding!!.toolbar
        setSupportActionBar(toolbar)
        toolBarLayout = binding!!.toolbarLayout
        toolBarLayout.title = ""

        imageFromApi = findViewById(R.id.imageFromApi)

        id = intent.getStringExtra("placeID")

        callingApiData()
    }

    private fun callingApiData() {
        id?.let {
            viewModel.discoverHerePlaceHereDeveloper(
                    "dmLgAQo631UJfwF5R2hH",
                    "391hkRjz5Z3Ee1h3wz6Kng",
                    "sharing",
                    it,
            )
        }//todo continue

        viewModel.discoverHerePlaceHereDeveloperLiveData.observe(this, {

            Log.d(TAG, "callingApiData: ${it.media.images.available}")

            if (it.media.images.available != 0)
                Glide.with(this).load(it.media.images.items[0].src).into(imageFromApi)

            Glide.with(this).load(it.icon).into(imageFromApi)

            binding!!.FeaturedItemTitle.text = it.name

            binding!!.FeaturedItemDetails.text = it.categories[0].title

            binding!!.FeaturedItemContacts.text = it.contacts.phone[0].value//label

            binding!!.FeaturedItemLocation.text = it.location.address.city

            url = it.view

            placeResponse = it

        })

    }

    companion object {
        private const val TAG = "ShowFeaturedPlace"
    }

    fun viewOnMap(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    @SuppressLint("CheckResult")
    fun savePlace(view: View) {

        val database: AppDatabase = AppDatabase.getInstance(this)
        database.placesDao().insertPlace(placeResponse)//todo test maybe here is the problem
                .subscribeOn(Schedulers.computation()).subscribe()

    }
}
