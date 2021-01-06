package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tammamkhalaf.myuaeguide.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Hotels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://hotels4.p.rapidapi.com/locations/search?query=dubai&locale=en_US")
                .get()
                .addHeader("x-rapidapi-key", "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0")
                .addHeader("x-rapidapi-host", "hotels4.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}