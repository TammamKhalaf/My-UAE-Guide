package com.tammamkhalaf.myuaeguide.repository;

import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.Geoname;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.OpenTripMapServiceApi;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.Places;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleFeature;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleSuggestFeature;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repository {

    private TrueWayPlacesServiceApi twpApiService;
    private HotelServiceApi hotelServiceApi;
    private OpenTripMapServiceApi openTripMapServiceApi;

    @Inject
    public Repository(TrueWayPlacesServiceApi twpApiService, HotelServiceApi hotelServiceApi,OpenTripMapServiceApi openTripMapServiceApi) {
        this.twpApiService = twpApiService;
        this.hotelServiceApi = hotelServiceApi;
        this.openTripMapServiceApi = openTripMapServiceApi;
    }

    /**
     * OpenTripApiModel
     * **/
    public Observable<Geoname> getGeoName(String lang, String placeName, String TwoChar, String apiKey) {
        return openTripMapServiceApi.getGeoName(lang, placeName, TwoChar, apiKey);
    }

    public Observable<ArrayList<SimpleFeature>> getAllPlaceInBBox(Double lon_min, Double lon_max, Double lat_min, Double lat_max, String osm_, String osm,
                                                                  String type, int rate, String json, int limit_max_500, String apiKey) {
        return openTripMapServiceApi.getAllPlaceInBBox(lon_min, lon_max, lat_min, lat_max, osm_,
                osm, type, rate, json, limit_max_500, apiKey);
    }

    public Observable<ArrayList<SimpleFeature>> getAllPlacesClosestToPoint(int radius, Double lon, Double lat,
                                                                           String _osm, String osm, String type, String json,
                                                                           int limit, String API_KEY){
        return openTripMapServiceApi.getAllPlacesClosestToPoint(radius,lon,lat, _osm, osm,
                 type,json, limit, API_KEY);
    }

    public Observable<ArrayList<SimpleSuggestFeature>> getSuggestionsClosestToPoint(int radius, Double lon, Double lat, String _osm,
                                                                         String osm, String type, String rate, String json,
                                                                         String base_or_address, int limit, String API_KEY){
        return openTripMapServiceApi.getSuggestionsClosestToPoint(radius,lon,lat,_osm, osm,type,
                rate, json, base_or_address, limit, API_KEY);
    }


    public Observable<Places> getDetailedInfoAboutPlace(String placeId,String API_KEY){
        return openTripMapServiceApi.getDetailedInfoAboutPlace(placeId,API_KEY);
    }

}
