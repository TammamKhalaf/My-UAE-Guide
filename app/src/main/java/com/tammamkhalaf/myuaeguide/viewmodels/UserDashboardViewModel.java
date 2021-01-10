package com.tammamkhalaf.myuaeguide.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.Location;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.Place;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.TrueWayPlacesResponse;
import com.tammamkhalaf.myuaeguide.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserDashboardViewModel extends ViewModel {
    private static final String TAG = "UserDashboardViewModel";
    private Repository repository;

    MutableLiveData<ArrayList<Place>> PlacesList = new MutableLiveData<>();

    @ViewModelInject
    public UserDashboardViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Place>> getPlacesList() {
        return PlacesList;
    }

    @SuppressLint("CheckResult")
    public void Places(HashMap<String, String> TrueWayPlacesFilterMap, String locationCoordinates, int radius, String type){
        repository.getTwpPlaces(TrueWayPlacesFilterMap,locationCoordinates,radius,type)
        .subscribeOn(Schedulers.io())
        .map(new Function<TrueWayPlacesResponse, ArrayList<Place>>() {
            @Override
            public ArrayList<Place> apply(TrueWayPlacesResponse trueWayPlacesResponse) throws Throwable {
                ArrayList<Place> placeList = (ArrayList<Place>) trueWayPlacesResponse.getResults();
                for(Place place:placeList){
                    String placeName = place.getName();
                    String placeAddress = place.getAddress();
                    String placeId = place.getId();
                    String placePhoneNumber = place.getPhoneNumber();
                    String placeWebsite = place.getWebsite();
                    Location placeLocation = place.getLocation();
                    List<String> placeTypes = place.getTypes();
                }
                return placeList;
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(result->PlacesList.setValue(result),Throwable-> Log.e(TAG, "ViewModel: error",Throwable));
    }
}
