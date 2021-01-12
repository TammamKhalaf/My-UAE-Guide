package com.tammamkhalaf.myuaeguide.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.DiscoverExploreResponse
import com.tammamkhalaf.myuaeguide.categories.openTripMap.Geoname
import com.tammamkhalaf.myuaeguide.categories.openTripMap.Places
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleFeature
import com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleSuggestFeature
import com.tammamkhalaf.myuaeguide.helperClasses.homeAdapter.featured.FeaturedHelperClass
import com.tammamkhalaf.myuaeguide.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class UserDashboardViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {
    var geonameMutableLiveData = MutableLiveData<Geoname>()
    var simpleFeatureMutableLiveData = MutableLiveData<ArrayList<SimpleFeature>>()
    var simpleSuggestClosestFeatureLiveData = MutableLiveData<ArrayList<SimpleSuggestFeature>>()
    var allPlacesInBBoxLiveData = MutableLiveData<ArrayList<SimpleFeature>>()
    var detailedInfoAboutPlaceLiveData = MutableLiveData<Places>()

    var allFavoritePlacesInBBoxLiveData: LiveData<ArrayList<FeaturedHelperClass>>? = null

    var discoverExplorePlacesHereDeveloperLiveData = MutableLiveData<DiscoverExploreResponse>()

    /***
     *
     * return data from repository - api
     * repository - api
     *
     * **/

    @SuppressLint("CheckResult")
    fun getGeoName(lang: String?, placeName: String?, TwoChar: String?, apiKey: String?) {
        repository.getGeoName(lang, placeName, TwoChar, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: Geoname -> geonameMutableLiveData.setValue(result) }
                ) { error: Throwable? -> Log.e(TAG, "getGeoName: ", error) }
    }

    fun getAllPlaceInBBox(lang: String?,lon_min: Double?, lon_max: Double?, lat_min: Double?, lat_max: Double?, osm_: String?, osm: String?, type: String?,
                          rate: Int, json: String?, limit_max_500: Int, apiKey: String?) {
        repository.getAllPlaceInBBox(lang,lon_min, lon_max, lat_min, lat_max, osm_, osm, type, rate, json, limit_max_500, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: ArrayList<SimpleFeature> -> allPlacesInBBoxLiveData.setValue(result) }
                ) { error: Throwable? -> Log.e(TAG, "getAllPlaceInBBox: ViewModel", error) }
    }

    fun getAllPlacesClosestToPoint(radius: Int, lon: Double?, lat: Double?, _osm: String?, osm: String?, type: String?, json: String?, limit: Int,
                                   API_KEY: String?) {
        repository.getAllPlacesClosestToPoint(radius, lon, lat, _osm, osm, type, json, limit, API_KEY).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: ArrayList<SimpleFeature> -> simpleFeatureMutableLiveData.setValue(result) }
                ) { error: Throwable? -> Log.e(TAG, "getAllPlacesClosestToPoint: ", error) }
    }

    fun getSuggestionsClosestToPoint(radius: Int, lon: Double?, lat: Double?, _osm: String?, osm: String?, type: String?, rate: String?, json: String?,
                                     base_or_address: String?, limit: Int, API_KEY: String?) {
        repository.getSuggestionsClosestToPoint(radius, lon, lat, _osm, osm, type, rate, json, base_or_address, limit, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: ArrayList<SimpleSuggestFeature> -> simpleSuggestClosestFeatureLiveData.setValue(result) }
                ) { error: Throwable? -> Log.e(TAG, "getSuggestionsClosestToPoint: ", error) }
    }

    fun getDetailedInfoAboutPlace(lang:String?,placeId: String?, API_KEY: String?) {
        repository.getDetailedInfoAboutPlace(lang,placeId, API_KEY).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: Places -> detailedInfoAboutPlaceLiveData.setValue(result) })
                { error: Throwable? -> Log.e(TAG, "getDetailedInfoAboutPlace: ", error) }
    }


    //at circle_or_bounding_box:String
    fun discoverExplorePlacesHereDeveloper(app_id:String, app_code:String, position:String, cat_list:List<String>){
        repository.discoverExplorePlacesHereDeveloper(app_id,app_code,position,cat_list)//circle_or_bounding_box
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result: DiscoverExploreResponse -> discoverExplorePlacesHereDeveloperLiveData.setValue(result) })
                { error: Throwable? -> Log.e(TAG, "discoverExplorePlacesHereDeveloper: ", error) }
    }


    companion object {
        private const val TAG = "UserDashboardViewModel"
    }
}