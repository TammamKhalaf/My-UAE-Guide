package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NearbyPlacesHelperClass {

    //todo learn background and okhttp and retrofit and change it to kotlin

    public static @Nullable ResponseBody getNearbyPlaces(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://trueway-places.p.rapidapi.com/FindPlacesNearby?location=37.783366%2C-122.402325&type=cafe&radius=150&language=en")
                .get()
                .addHeader("x-rapidapi-key", "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0")
                .addHeader("x-rapidapi-host", "trueway-places.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
