package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrueWayPlacesService {
    final static String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    final static String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    //todo add headers to call and test
    // .addHeader("x-rapidapi-key", rapidApiKey)
    // .addHeader("x-rapidapi-host", rapidApiHost)


    @GET("findPlacesByText")
    Call<Result> findPlacesByText(@Query("text")String text,@Query("language")String language);

    @GET("findPlacesNearby")
    Call<List<Result>> findPlacesNearby(@Query("lat") double lat,@Query("lng")double lng,@Query("type")String type,@Query("radius")int radius,@Query("language") String language);

}
