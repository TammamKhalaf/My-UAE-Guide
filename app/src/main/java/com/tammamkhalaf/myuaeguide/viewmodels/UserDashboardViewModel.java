package com.tammamkhalaf.myuaeguide.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tammamkhalaf.myuaeguide.categories.openTripMap.Geoname;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.Places;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleFeature;
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleSuggestFeature;
import com.tammamkhalaf.myuaeguide.repository.Repository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDashboardViewModel extends ViewModel {
    private static final String TAG = "UserDashboardViewModel";
    private Repository repository;

    MutableLiveData<Geoname> geonameMutableLiveData = new MutableLiveData<>();

    MutableLiveData<ArrayList<SimpleFeature>> simpleFeatureMutableLiveData = new MutableLiveData<>();

    MutableLiveData<ArrayList<SimpleSuggestFeature>> simpleSuggestClosestFeatureLiveData = new MutableLiveData<>();

    MutableLiveData<ArrayList<SimpleFeature>> allPlacesInBBoxLiveData = new MutableLiveData<>();

    MutableLiveData<Places> detailedInfoAboutPlaceLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<SimpleFeature>> getAllPlacesInBBoxLiveData() {
        return allPlacesInBBoxLiveData;
    }

    public MutableLiveData<Places> getDetailedInfoAboutPlaceLiveData() {
        return detailedInfoAboutPlaceLiveData;
    }

    public MutableLiveData<Geoname> getGeonameMutableLiveData() {
        return geonameMutableLiveData;
    }

    public MutableLiveData<ArrayList<SimpleFeature>> getSimpleFeatureMutableLiveData() {
        return simpleFeatureMutableLiveData;
    }

    public MutableLiveData<ArrayList<SimpleSuggestFeature>> getSimpleSuggestClosestFeatureLiveData() {
        return simpleSuggestClosestFeatureLiveData;
    }

    @ViewModelInject
    public UserDashboardViewModel(Repository repository) {
        this.repository = repository;
    }


    @SuppressLint("CheckResult")
    public void getGeoName(String lang, String placeName, String TwoChar, String apiKey) {
        repository.getGeoName(lang,placeName,TwoChar,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->geonameMutableLiveData.setValue(result),
                        error-> Log.e(TAG, "getGeoName: ",error));
        ;
    }
    
    public void getAllPlaceInBBox(Double lon_min, Double lon_max, Double lat_min, Double lat_max, String osm_, String osm, String type,
                                  int rate, String json, int limit_max_500, String apiKey){
        repository.getAllPlaceInBBox(lon_min,lon_max,lat_min,lat_max,osm_,osm,type,rate, json,limit_max_500,apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->simpleFeatureMutableLiveData.setValue(result),
                        error -> Log.e(TAG, "getAllPlaceInBBox: ViewModel",error));
    }



    public void getAllPlacesClosestToPoint(int radius, Double lon, Double lat, String _osm, String osm, String type, String json, int limit,
                                           String API_KEY){
        repository.getAllPlacesClosestToPoint(radius,lon,lat,_osm,osm,type,json,limit,API_KEY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->allPlacesInBBoxLiveData.setValue(result),
                        error -> Log.e(TAG, "getAllPlacesClosestToPoint: ",error));;

    }

    public void getSuggestionsClosestToPoint(int radius, Double lon, Double lat, String _osm, String osm, String type, String rate, String json,
                                                        String base_or_address, int limit, String API_KEY){
        repository.getSuggestionsClosestToPoint(radius,lon,lat,_osm,osm,type,rate,json,base_or_address,limit,API_KEY).subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result-> simpleSuggestClosestFeatureLiveData.setValue(result),
                    error-> Log.e(TAG, "getSuggestionsClosestToPoint: ",error))
        ;
    }

    public void getDetailedInfoAboutPlace(String placeId,String API_KEY){
        repository.getDetailedInfoAboutPlace(placeId,API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .subscribe(result->detailedInfoAboutPlaceLiveData.setValue(result),error-> Log.e(TAG, "getDetailedInfoAboutPlace: ",error));
    }

}

