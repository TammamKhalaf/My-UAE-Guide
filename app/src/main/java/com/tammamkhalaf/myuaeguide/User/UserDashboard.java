package com.tammamkhalaf.myuaeguide.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.tammamkhalaf.myuaeguide.Common.LoginSignup.RetailerStartUpScreen;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.CategoriesAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.CategoriesHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.FeaturedAdapter;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.FeaturedHelperClass;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewedAdpater;
import com.tammamkhalaf.myuaeguide.HelperClasses.HomeAdapter.MostViewedHelperClass;
import com.tammamkhalaf.myuaeguide.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "UserDashboard";

    RecyclerView featuredRecycler, mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    ImageView menuIcon;

    LinearLayout content;

    static final float END_SCALE = 0.7f;



    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        /***
         *
         * my code below
         * */

        findPlacesByText("Children's Creativity Museum", "en",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            // Do what you want to do with the response.
                            Log.d(TAG, "findPlacesByText response: " + responseStr);
                            showResults(responseStr);
                        } else {
                            // Request not successful
                        }
                    }
                });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        findPlacesNearby(37.783366,-122.402325, "cafe", 150, "en",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // Something went wrong
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String responseStr = response.body().string();
                            // Do what you want to do with the response.
                            Log.d(TAG, "findPlacesNearby response: " + responseStr);
                            showResults(responseStr);
                        } else {
                            // Request not successful
                        }
                    }
                });
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else {
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
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_all_categories) {
            startActivity(new Intent(getApplicationContext(), AllCategories.class));
        } else {
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        return true;
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
        GradientDrawable gradient2 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffd4cbe5, 0xffd4cbe5});
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xff7adccf, 0xff7adccf});
        GradientDrawable gradient3 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xfff7c59f, 0xFFf7c59f});
        GradientDrawable gradient4 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffb8d7f5, 0xffb8d7f5});


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


    public void callRetailerScreen(View view) {
        startActivity(new Intent(getApplicationContext(), RetailerStartUpScreen.class));
    }

    /***
     * test rapid api
     *
     * my code below
     * */


    private final static String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    private final static String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    private final OkHttpClient client = new OkHttpClient();

    public void getRapidApiAsync(String url, String rapidApiKey, String rapidApiHost, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", rapidApiKey)
                .addHeader("x-rapidapi-host", rapidApiHost)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void findPlacesByText(String text, String language, Callback callback) {
        getRapidApiAsync(String.format(Locale.US, "https://%s/FindPlaceByText?text=%s&language=%s", RAPIDAPI_TRUEWAY_PLACES_HOST, text, language),
                RAPIDAPI_KEY,
                RAPIDAPI_TRUEWAY_PLACES_HOST,
                callback);
    }

    public void findPlacesNearby(double lat, double lng, String type, int radius, String language, Callback callback) {
        getRapidApiAsync(String.format(Locale.US, "https://%s/FindPlacesNearby?location=%.6f,%.6f&type=%s&radius=%d&language=%s", RAPIDAPI_TRUEWAY_PLACES_HOST, lat, lng, type, radius, language),
                RAPIDAPI_KEY,
                RAPIDAPI_TRUEWAY_PLACES_HOST,
                callback);
    }

    private void showResults(String responseStr) {
        try {
            JSONObject jsonObj = new JSONObject(responseStr);
            // Getting results JSON Array node
            JSONArray results = jsonObj.getJSONArray("results");
            Log.d(TAG, "found places: " + results.length());
            // looping through All Results
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String id = result.getString("id");
                String name = result.getString("name");
                String address = result.getString("address");
                JSONObject location = result.getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                Integer distance = result.has("distance") ? result.getInt("distance") : 0; // present for FindPlacesNearby only
                Log.d(TAG, String.format(Locale.US,"result[%s]: id=%s; name=%s; address=%s; lat=%.6f; lng=%.6f; distance=%d", i, id, name, address, lat, lng, distance));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}