package com.tammamkhalaf.myuaeguide.categories.hotels.network

import com.tammamkhalaf.myuaeguide.categories.hotels.getAllPropertyInfoModel.Data
import com.tammamkhalaf.myuaeguide.categories.hotels.getAllPropertyInfoModel.PropertyInfo
import com.tammamkhalaf.myuaeguide.categories.hotels.getHotelPhotosModel.Hotel
import com.tammamkhalaf.myuaeguide.categories.hotels.metaDataModel.MetaData
import com.tammamkhalaf.myuaeguide.categories.hotels.searchHotelModel.HotelResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*
import java.util.*

interface HotelServiceApi {
    //endregion
    //region search hotel
    /**
     * x-rapidapi-key:cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0
     * x-rapidapi-host:hotels4.p.rapidapi.com
     *
     * 1
     * Search for related locations and suggestions
     */
    @Headers("x-rapidapi-key:$RAPIDAPI_KEY", "x-rapidapi-host:$RAPIDAPI_HOTEL")
    @GET("locations/search")
    fun searchHotel(@Query("query") query: String?,
                    @Query("locale") language: String?): Observable<HotelResponse?>?
    //endregion
    //region get Hotel Photos
    /**
     * 2
     * Get all available photos of property
     */
    //todo return Types Hotel and list of hotel images, room images-->
    //todo -->featuredImageTrackingDetails, and propertyImageTrackingDetails
    @Headers("x-rapidapi-key:$RAPIDAPI_KEY",
            "x-rapidapi-host:$RAPIDAPI_HOTEL")
    @GET("properties/get-hotel-photos/{id}")
    fun getHotelPhotos(@Path("id") id: Int): Observable<Hotel?>?
    //endregion
    //region get all property info
    //todo part 3 part 2 completed
    /**
     * 3
     * Get all available information of a property
     */
    @Headers("x-rapidapi-key:$RAPIDAPI_KEY",
            "x-rapidapi-host:$RAPIDAPI_HOTEL")
    @GET("properties/get-details")
    fun  //todo create response pojo class
    //todo checkIn,checkOut,locale,currency
            getPropertyInfo(@Query("id") id: Int,
                            @Query("adults") Adults: Int,
                            @QueryMap filters: HashMap<String?,
                                    String?>?): Observable<PropertyInfo?>?
    //endregion
    //region get meta data
    /***
     * 4
     * Get locale meta data
     *
     */  //todo create response pojo class
    @get:GET("get-meta-data")
    @get:Headers("x-rapidapi-key:$RAPIDAPI_KEY",
            "x-rapidapi-host:$RAPIDAPI_HOTEL")
    val metaData: Observable<MetaData?>?
    //endregion

    //region list properties
    /**
     * 5
     * List properties with options and filters
     */
    @Headers("x-rapidapi-key:$RAPIDAPI_KEY",
            "x-rapidapi-host:$RAPIDAPI_HOTEL")
    @GET("properties/list")
    fun listProperties(@QueryMap filterA: HashMap<String?, Int?>?,  //todo add in this filter destinationId,pageNumber,pageSize,adults1
                       @QueryMap filterB: HashMap<String?, String?>?,  //todo add in this filter checkIn,checkOut,currency,locale
                       @Query("sortOrder") price: Double): Observable<Data?>? //endregion

    companion object {
        //region headers key
        const val BASE_URL = "https://hotels4.p.rapidapi.com"
        const val RAPIDAPI_KEY = "cb558f9b05mshc727d913f6cde72p158d4fjsn1c4bf14f36d0"
        const val RAPIDAPI_HOTEL = "hotels4.p.rapidapi.com"
    }
}