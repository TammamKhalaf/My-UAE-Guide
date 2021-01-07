package com.tammamkhalaf.myuaeguide.User;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Gist;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Result;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.ServiceBuilder;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.TrueWayPlacesService;
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.RetailerStartUpScreen;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedAdpater;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedHelperClass;
import com.tammamkhalaf.myuaeguide.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "UserDashboard";
    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuIcon;

    LinearLayout content;
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Call<Gist> call;
    ArrayList<Result> results;

    //todo add section for the mahrajanat in uae mahrajan zayed etc ....

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_dashboard);


        //Hooks
        featuredRecycler = findViewById(R.id.featured_recycler);
        mostViewedRecycler = findViewById(R.id.most_viewed_recycler);
        categoriesRecycler = findViewById(R.id.categories_recycler);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        menuIcon = findViewById(R.id.menu_icon);

        content = findViewById(R.id.content);


        //Functions will be executed automatically when this activity will be created
        featuredRecycler();
        mostViewedRecycler();
        categoriesRecycler();
        navigationDrawer();
    }

    //region navigation drawer

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.banner_background_light));//Color.TRANSPARENT
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = content.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_all_categories) {
            startActivity(new Intent(getApplicationContext(), AllCategories.class));
        } else {
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
    //endregion

    //region featured
    private void featuredRecycler() {

        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();

        //todo tomorrow try api and complete retrofit

        TrueWayPlacesService service = ServiceBuilder.buildService(TrueWayPlacesService.class);

        int[] images = {R.drawable.sheikh_zayed_grand_mosque, R.drawable.burj_al_arab};
        // todo add array of images

        // todo 429 Too Many Requests
        //  todo use Rxjava to separate time of consuming api
        /**
         *
         This gets thrown (by OkHttp, not Retrofit) when there are more than 20 redirects when calling an endpoint.
         Usually this indicates a redirect cycle between two endpoints.
         Both Chrome and Firefox will also stop loading the request after this many redirects and fail the request.
         You need to consult with your server team or endpoint documentation to ensure you are passing the correct data directly to the endpoint you want to call.
         No action for Retrofit to take here.
         * **/

        //call = service.findPlacesByText(place, "en");
        //todo location coordinates =
        String locationCoordinates = "24.199168,55.719391";
        call = service.findPlacesNearby(locationCoordinates, "cafe", 10000, "en");

        call.enqueue(new Callback<Gist>() {
            @Override
            public void onResponse(Call<Gist> call, Response<Gist> response) {
                results = (ArrayList<Result>) response.body().getResults();
                for (Result place : results) {
                    featuredLocations.add(new FeaturedHelperClass(R.drawable.coffee_shop,
                            place.getName(),
                            place.getPhoneNumber()));
                }
                adapter = new FeaturedAdapter(featuredLocations,UserDashboard.this);
                featuredRecycler.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Gist> call, Throwable t) {
            }
        });

    }
    //endregion

    //region categories
    private void categoriesRecycler() {

        //All Gradients
        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


        ArrayList<CategoriesHelperClass> categoriesHelperClasses = new ArrayList<>();
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.school_cat, getString(R.string.education)));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient2, R.drawable.hospital_cat, getString(R.string.hospital)));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient3, R.drawable.res_cat, getString(R.string.restaurant)));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient4, R.drawable.shopping_cat, getString(R.string.shopping)));
        categoriesHelperClasses.add(new CategoriesHelperClass(gradient1, R.drawable.transport_cat, getString(R.string.transport)));


        categoriesRecycler.setHasFixedSize(true);
        adapter = new CategoriesAdapter(categoriesHelperClasses);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(adapter);

    }
    //endregion

    //region mostViewed
    private void mostViewedRecycler() {

        mostViewedRecycler.setHasFixedSize(true);
        mostViewedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.sheikh_zayed_grand_mosque, "Abu-Dhabi"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.burj_al_arab, "Dubai"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "Sharjah"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "Ajman"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "Ras-Alkhiemah"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "Um-Alquiwen"));
        mostViewedLocations.add(new MostViewedHelperClass(R.drawable.macdonald, "Al-fujairah"));

        adapter = new MostViewedAdpater(mostViewedLocations);
        mostViewedRecycler.setAdapter(adapter);

    }
    //endregion

    public void callRetailerScreen(View view) {
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

}