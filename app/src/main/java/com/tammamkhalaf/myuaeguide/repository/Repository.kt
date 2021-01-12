package com.tammamkhalaf.myuaeguide.repository


import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.DiscoverExploreResponse
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.hereDeveloperApiService
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi
import com.tammamkhalaf.myuaeguide.categories.openTripMap.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.collections.ArrayList

class Repository {

    private val twpApiService: TrueWayPlacesServiceApi
    private val hotelServiceApi: HotelServiceApi
    private val openTripMapServiceApi: OpenTripMapServiceApi
    private val hereDeveloperService: hereDeveloperApiService


    @Inject
    constructor(twpApiService: TrueWayPlacesServiceApi,
                hotelServiceApi: HotelServiceApi,
                openTripMapServiceApi: OpenTripMapServiceApi,
                hereDeveloperService: hereDeveloperApiService) {
        this.twpApiService = twpApiService
        this.hotelServiceApi = hotelServiceApi
        this.openTripMapServiceApi = openTripMapServiceApi
        this.hereDeveloperService = hereDeveloperService
    }


    /**
     * return data from api
     * OpenTripApiModel
     */
    fun getGeoName(lang: String?, placeName: String?, TwoChar: String?, apiKey: String?): Observable<Geoname> {
        return openTripMapServiceApi.getGeoName(lang, placeName, TwoChar, apiKey)
    }

    fun getAllPlaceInBBox(lang: String?,lon_min: Double?, lon_max: Double?, lat_min: Double?, lat_max: Double?, osm_: String?, osm: String?,
                          type: String?, rate: Int, json: String?, limit_max_500: Int, apiKey: String?): Observable<ArrayList<SimpleFeature>> {
        return openTripMapServiceApi.getAllPlaceInBBox(lang,lon_min, lon_max, lat_min, lat_max, osm_,
                osm, type, rate, json, limit_max_500, apiKey)
    }

    fun getAllPlacesClosestToPoint(radius: Int, lon: Double?, lat: Double?,
                                   _osm: String?, osm: String?, type: String?, json: String?,
                                   limit: Int, API_KEY: String?): Observable<ArrayList<SimpleFeature>> {
        return openTripMapServiceApi.getAllPlacesClosestToPoint(radius, lon, lat, _osm, osm,
                type, json, limit, API_KEY)
    }

    fun getSuggestionsClosestToPoint(radius: Int, lon: Double?, lat: Double?, _osm: String?,
                                     osm: String?, type: String?, rate: String?, json: String?,
                                     base_or_address: String?, limit: Int, API_KEY: String?): Observable<ArrayList<SimpleSuggestFeature>> {
        return openTripMapServiceApi.getSuggestionsClosestToPoint(radius, lon, lat, _osm, osm, type,
                rate, json, base_or_address, limit, API_KEY)
    }

    fun getDetailedInfoAboutPlace(lang:String?,placeId: String?, API_KEY: String?): Observable<Places> {
        return openTripMapServiceApi.getDetailedInfoAboutPlace(lang,placeId,API_KEY)
    }

    fun discoverExplorePlacesHereDeveloper(app_id:String,
                                            app_code:String,
                                            position:String,//  circle_or_bounding_box:String,
                                           cat_list:List<String>)
    :Observable<DiscoverExploreResponse>? {// in or at circle_or_bounding_box
        return hereDeveloperService.getRecommendedPlaces(app_id,app_code,position,cat_list)
    }

    companion object {
        private const val TAG = "Repository"
    }
}