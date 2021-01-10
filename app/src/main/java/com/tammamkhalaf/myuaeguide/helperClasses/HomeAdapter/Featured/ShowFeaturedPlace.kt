package com.tammamkhalaf.myuaeguide.helperClasses.HomeAdapter.Featured

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tammamkhalaf.myuaeguide.databinding.ActivityShowFeaturedPlaceBinding

class ShowFeaturedPlace : AppCompatActivity() {
    //This works if you have used a variable in your <data> tag and you have built your project afterwards, if you don't have an activity
    var binding: ActivityShowFeaturedPlaceBinding? = null

    //if you have an activity, you can use setContentView from the DataBindingUtils. Don't forget to delete the generic setContentView
    //ActivityShowFeaturedPlaceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_featured_place);
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityShowFeaturedPlaceBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)
        val toolbar = binding!!.toolbar
        setSupportActionBar(toolbar)
        val toolBarLayout = binding!!.toolbarLayout
        toolBarLayout.title = title
        //todo
        val intent = intent
        val title = intent.getStringExtra("featuredItemTitle")
        binding!!.nestedContent.FeaturedItemTitle.text = title
        val details = intent.getStringExtra("featuredItemDescription")
        binding!!.nestedContent.FeaturedItemDetails.text = details
        intent.getIntExtra("featuredItemImage", 0)
        val fab = binding!!.fab
        fab.setOnClickListener { view: View? ->
            Snackbar.make(view!!, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}