package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TrueWayPlacesService {
    public static final String BASE_URL = "https://trueway-places.p.rapidapi.com/";
    String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    String RAPIDAPI_TRUEWAY_PLACES_HOST = "trueway-places.p.rapidapi.com";

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @GET("FindPlaceByText")
    Call<Results> findPlacesByText(@Query("text")String text, @Query("language")String language);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @GET("FindPlacesNearby")//Map<Object,Object>
    Call<Results> findPlacesNearby(@QueryMap HashMap<String,String> filters, @Query("radius")int radius, @Query("language") String language);

    //todo this just for demonstration purposes >>>
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @POST("places")//or we can send map Of Objects Map<Object>,Object>>
    Call<Place> addPlace(@Body Place newPlace);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @FormUrlEncoded
    @PUT("places/{id}")
    Call<Place> updatePlaceById(@Path("id")int ID,
                                @Field("name")String name,
                                @Field("address")String address,
                                @Field("phone_number")String phoneNumber,
                                @Field("website")String website,
                                @Field("location")Location location,
                                @Field("types")List<String> types);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "x-rapidapi-key:"+RAPIDAPI_KEY,
            "x-rapidapi-host:"+RAPIDAPI_TRUEWAY_PLACES_HOST
    })
    @DELETE("places/{id}")
    Call<Void> deleteSpecificPlace(@Path("id")int ID);


}
