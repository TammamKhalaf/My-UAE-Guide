package com.tammamkhalaf.myuaeguide.repository

import android.util.Log
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi
import com.tammamkhalaf.myuaeguide.categories.openTripMap.*
import io.reactivex.rxjava3.core.Observable
import java.util.*
import javax.inject.Inject

class Repository @Inject constructor(private val twpApiService: TrueWayPlacesServiceApi, private val hotelServiceApi: HotelServiceApi,
                                     private val openTripMapServiceApi: OpenTripMapServiceApi) {

    /**
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

    companion object {
        private const val TAG = "Repository"
    }
}