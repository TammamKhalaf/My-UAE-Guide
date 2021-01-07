package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TrueWayPlacesService {
    String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    @Headers({
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST,
            "Content-Type:application/x-www-form-urlencoded",
    })
    @GET("FindPlaceByText")//Map<List<Object>,List<Object>>
    Call<Gist> findPlacesByText(@Query("text")String text, @Query("language")String language);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @GET("findPlacesNearby")
    Call<List<Result>> findPlacesNearby(@Query("lat") double lat,@Query("lng")double lng,@Query("type")String type,@Query("radius")int radius,@Query("language") String language);

}
