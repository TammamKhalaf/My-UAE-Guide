package com.tammamkhalaf.myuaeguide.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewedAdpater;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.tammamkhalaf.myuaeguide.R;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity {

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1, gradient2, gradient3, gradient4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);

        //Functions will be executed automatically when this activity will be created
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
    }

    private void featuredRecycler() {


        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        featuredLocations.add(new FeaturedHelperClass(R.drawable.macdonald,"Mc'Donald's",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.burj_al_arab,"Dubai",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."));
        featuredLocations.add(new FeaturedHelperClass(R.drawable.sheikh_zayed_grand_mosque,"Abu-Dhabi",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor."));

        adapter = new FeaturedAdapter(featuredLocations);

        featuredRecycler.setAdapter(adapter);

    }

    private void categoriesRecycler() {

        //All Gradients
        gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.school_cat, "Education"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.hospital_cat, "HOSPITAL"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.res_cat, "Restaurant"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.shopping_cat, "Shopping"));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.transport_cat, "Transport"));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }

    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "McDonald's"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.burj_al_arab, "Dubai"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.sheikh_zayed_grand_mosque, "Abu-Dhabi"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "MacDonald's"));

        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }



}