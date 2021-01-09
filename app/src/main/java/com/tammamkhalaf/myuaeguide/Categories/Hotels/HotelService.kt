package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import com.tammamkhalaf.myuaeguide.Categories.Hotels.GetAllPropertyInfoRegionThree.Data;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.GetAllPropertyInfoRegionThree.PropertyInfo;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.GetHotelPhotosRegionTwo.Hotel;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.MetaDataRegionFour.MetaData;
import com.tammamkhalaf.myuaeguide.Categories.Hotels.searchHotelRegionOne.Properties;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces.TrueWayPlacesService.RAPIDAPI_TRUEWAY_PLACES_HOST;

public interface HotelService {
    //region headers key
    String BASE_URL = "https://hotels4.p.rapidapi.com";
    String RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0";
    String RAPIDAPI_HOTEL = "hotels4.p.rapidapi.com";
    //endregion

    //region search hotel
    /**
     * x-rapidapi-key:cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0
     * x-rapidapi-host:hotels4.p.rapidapi.com
     *
     * 1
     * Search for related locations and suggestions
     */
    @Headers({"x-rapidapi-key:"+RAPIDAPI_KEY, "x-rapidapi-host:"+ RAPIDAPI_HOTEL})
    @GET("locations/search")
    Call<Properties> searchHotel(@Query("query") String query, @Query("locale") String language);
    //endregion

    //region get Hotel Photos
    /**
     * 2
     * Get all available photos of property
     *///todo return Types Hotel and list of hotel images, room images-->
     //todo -->featuredImageTrackingDetails, and propertyImageTrackingDetails
    @Headers({"x-rapidapi-key:"+RAPIDAPI_KEY, "x-rapidapi-host:"+ RAPIDAPI_HOTEL})
    @GET("properties/get-hotel-photos/{id}")
    Call<Hotel> getHotelPhotos(@Path("id") int id);
    //endregion

    //region get all property info
    //todo part 3 part 2 completed
    /**
     * 3
     * Get all available information of a property
     */
    @Headers({"x-rapidapi-key:"+RAPIDAPI_KEY, "x-rapidapi-host:"+ RAPIDAPI_HOTEL})
    @GET("properties/get-details")
    //todo create response pojo class
    //todo checkIn,checkOut,locale,currency
    Call<PropertyInfo> getPropertyInfo(@Query("id") int id, @Query("adults") int Adults, @QueryMap() HashMap<String,String> filters);
    //endregion

    //region get meta data
    /***
     * 4
     * Get locale meta data
     *
     * **/
    @Headers({"x-rapidapi-key:"+RAPIDAPI_KEY, "x-rapidapi-host:"+ RAPIDAPI_HOTEL})
    @GET("get-meta-data")//todo create response pojo class
    Call<MetaData> getMetaData();
    //endregion

    //region list properties
    /**
     * 5
     * List properties with options and filters
     **/
    @Headers({"x-rapidapi-key:"+RAPIDAPI_KEY, "x-rapidapi-host:"+ RAPIDAPI_HOTEL})
    @GET("properties/list")
    Call<Data> listProperties(@QueryMap HashMap<String,Integer> filterA,//todo add in this filter destinationId,pageNumber,pageSize,adults1
                              @QueryMap HashMap<String,String> filterB,//todo add in this filter checkIn,checkOut,currency,locale
                              @Query("sortOrder") double price);
    //endregion
}