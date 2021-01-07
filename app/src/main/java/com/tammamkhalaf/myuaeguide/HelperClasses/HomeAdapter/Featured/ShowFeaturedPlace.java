package com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tammamkhalaf.myuaeguide.R;
import com.tammamkhalaf.myuaeguide.databinding.ActivityShowFeaturedPlaceBinding;
import com.tammamkhalaf.myuaeguide.databinding.ContentScrollingBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


public class ShowFeaturedPlace extends AppCompatActivity {


    //This works if you have used a variable in your <data> tag and you have built your project afterwards, if you don't have an activity
    ActivityShowFeaturedPlaceBinding binding;

    //if you have an activity, you can use setContentView from the DataBindingUtils. Don't forget to delete the generic setContentView
    //ActivityShowFeaturedPlaceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_featured_place);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityShowFeaturedPlaceBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        //todo

        Intent intent = getIntent();
        String title = intent.getStringExtra("featuredItemTitle");
        binding.nestedContent.FeaturedItemTitle.setText(title);
        String details = intent.getStringExtra("featuredItemDescription");
        binding.nestedContent.FeaturedItemDetails.setText(details);
        intent.getIntExtra("featuredItemImage",0);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }


}