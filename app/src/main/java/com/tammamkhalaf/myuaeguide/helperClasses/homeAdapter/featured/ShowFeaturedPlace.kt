package com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tammamkhalaf.myuaeguide.databinding.ActivityShowFeaturedPlaceBinding
import com.tammamkhalaf.myuaeguide.viewmodels.ShowFeaturedPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowFeaturedPlace : AppCompatActivity() {
    //This works if you have used a variable in your <data> tag and you have built your project afterwards, if you don't have an activity
    var binding: ActivityShowFeaturedPlaceBinding? = null
    private lateinit var viewModel: ShowFeaturedPlaceViewModel

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
        val toolBarLayout = binding!!.toolbarLayout
        toolBarLayout.title = ""



        //todo
        val intent = intent
        val title = intent.getStringExtra("featuredItemTitle")
        binding!!.FeaturedItemTitle.text = title
        val details = intent.getStringExtra("featuredItemDescription")
        binding!!.FeaturedItemDetails.text = details
        intent.getIntExtra("featuredItemImage", 0)

        callingApiData()
    }

    private fun callingApiData() {
        viewModel.discoverHerePlaceHereDeveloper(
                "dmLgAQo631UJfwF5R2hH",
                "391hkRjz5Z3Ee1h3wz6Kng",
                "sharing",
                "784thqej-0c5fbeadf8b94059acbdcea7dc41bbf3")//todo continue

        viewModel.discoverHerePlaceHereDeveloperLiveData.observe(this, {
            Log.d(TAG, "callingApiData: ${it.name}")
        })

    }

    companion object {
        private const val TAG = "ShowFeaturedPlace"
    }


}