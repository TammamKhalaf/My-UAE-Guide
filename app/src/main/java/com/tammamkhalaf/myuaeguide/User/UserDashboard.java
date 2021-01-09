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
import com.tammamkhalaf.myuaeguide.Categories.Hotels.HotelService;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.HotelServiceBuilder;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.searchHotelRegionOne.Properties;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.searchHotelRegionOne.Suggestion;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Results;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.Place;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.ServiceBuilder;
import com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.TrueWayPlacesService;
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.RetailerStartUpScreen;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Categories.CategoriesHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.Featured.FeaturedHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewed.MostViewedHelperClass;
import com.tammamkhalaf.myuaeguide.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    Call<Results> call;
    ArrayList<Place> places;
    ArrayList<Suggestion> suggestions;

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

        TrueWayPlacesService service = ServiceBuilder.buildService(TrueWayPlacesService.class);

        int[] images = {R.drawable.sheikh_zayed_grand_mosque, R.drawable.burj_al_arab};
        // todo add array of images

        // todo 429 Too Many Requests
        //  todo use Rxjava to separate time of consuming api

        HashMap<String, String> TrueWayPlacesFilterMap = new HashMap<>();

        String locationCoordinates = "24.199168,55.719391";

        TrueWayPlacesFilterMap.put("location", locationCoordinates);
        /**
         * type >>>>
         airport,amusement_park,aquarium,art_gallery,atm,
         bakery,bank,bar,beauty_salon,bicycle_store,book_store,bowling,bus_station
         cafe,campground,car_dealer,car_rental,car_repair,car_wash,casino,cemetery,church,cinema,city_hall,clothing_store,convenience_store,courthouse
         dentist,department_store,doctor,electrician,electronics_store,embassy
         fire_station,flowers_store,funeral_service,furniture_store,gas_station
         government_office,grocery_store,gym,
         hairdressing_salon,hardware_store,homegoodsstore,hospital,insurance_agency,jewelry_store
         laundry,lawyer,library,liquor_store,locksmith,lodging,mosque,museum,night_club
         park,parking,pet_store,pharmacy,plumber,police_station,post_office,primary_school,
         rail_station,realestateagency,restaurant,rv_park
         school,secondary_school,shoe_store,shopping_center,spa,stadium,storage,store,subway_station,supermarket,synagogue
         taxi_stand,temple,tourist_attraction,train_station,transit_station,travel_agency,university
         veterinarian,zoo
         * */
        TrueWayPlacesFilterMap.put("type", "cafe");

        call = service.findPlacesNearby(TrueWayPlacesFilterMap, 10000, "en");

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    places = (ArrayList<Place>) response.body().getResults();
                    for (Place place : places) {
                        featuredLocations.add(new FeaturedHelperClass(R.drawable.coffee_shop, place.getName(), place.getPhoneNumber()));
                    }
                    adapter = new FeaturedAdapter(featuredLocations, UserDashboard.this);
                    featuredRecycler.setAdapter(adapter);
                } else if (response.code() == 401) {
                    Toast.makeText(UserDashboard.this, R.string.SessionExpired, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDashboard.this, R.string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(UserDashboard.this, R.string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserDashboard.this, R.string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show();
                }
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

        HotelService service = HotelServiceBuilder.buildService(HotelService.class);

        Call<Properties> call = service.searchHotel("dubai", "en_US");

        ArrayList<MostViewedHelperClass> mostViewedLocations = new ArrayList<>();


        call.enqueue(new Callback<Properties>() {
            @Override
            public void onResponse(Call<Properties> call, Response<Properties> response) {
                if (response.isSuccessful()) {
                    suggestions = (ArrayList<Suggestion>) response.body().suggestions;
                    Log.d(TAG, "onResponse: suggestions" + suggestions);
                    for (Suggestion suggestion : suggestions) {
                        for (int i = 0; i < suggestion.entities.size(); i++) {
                            mostViewedLocations.add(new MostViewedHelperClass(R.drawable.beds,
                                    suggestion.entities.get(i).name,
                                    suggestion.group));
                        }
                    }
                    adapter = new MostViewedAdapter(mostViewedLocations);
                    mostViewedRecycler.setAdapter(adapter);
                } else if (response.code() == 401) {
                    Toast.makeText(UserDashboard.this, R.string.SessionExpired, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: Not-Successful Response Code = " + response.code() +
                            "-->>> Response Error Body:" + response.errorBody().toString());
                    Toast.makeText(UserDashboard.this, R.string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Properties> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(UserDashboard.this, R.string.AConnectionErrorOccurred, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                } else {
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(UserDashboard.this, R.string.FailedToRetrieveItems, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    //endregion

    public void callRetailerScreen(View view) {
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

}