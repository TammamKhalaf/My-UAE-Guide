package com.tammamkhalaf.myuaeguide.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.DiscoverExploreResponse
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.DiscoverHereResponse
import com.tammamkhalaf.myuaeguide.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class UserDashboardViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {
    var discoverExplorePlacesHereDeveloperLiveData = MutableLiveData<DiscoverExploreResponse>()

    var discoverHerePlacesHereDeveloperLiveData = MutableLiveData<DiscoverHereResponse>()


    /***
     *
     * return data from repository - api
     * repository - api
     *
     * **/


    //at circle_or_bounding_box:String
    fun discoverExplorePlacesHereDeveloper(app_id:String, app_code:String, position:String, cat_list:List<String>){
        repository.discoverExplorePlacesHereDeveloper(app_id,app_code,position,cat_list)//circle_or_bounding_box
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result: DiscoverExploreResponse -> discoverExplorePlacesHereDeveloperLiveData.setValue(result) })
                { error: Throwable? -> Log.e(TAG, "discoverExplorePlacesHereDeveloper: ", error) }
    }


    fun discoverHerePlacesHereDeveloper(app_id:String, app_code:String, position:String, cat_list:List<String>){
        repository.discoverHerePlacesHereDeveloper(app_id,app_code,position,cat_list)//circle_or_bounding_box
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result: DiscoverHereResponse -> discoverHerePlacesHereDeveloperLiveData.setValue(result) })
                { error: Throwable? -> Log.e(TAG, "discoverHerePlacesHereDeveloper: ", error) }
    }


    companion object {
        private const val TAG = "UserDashboardViewModel"
    }
}