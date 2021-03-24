package com.tammamkhalaf.kmmsharedmodule.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse
import com.tammamkhalaf.myuaeguide.repository.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ShowFeaturedPlaceViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    var discoverHerePlaceHereDeveloperLiveData = MutableLiveData<PlaceResponse>()

    fun discoverHerePlaceHereDeveloper(app_id:String, app_code:String,pvid:String,id:String){
        repository.discoverHerePlaceHereDeveloper(pvid,app_id,app_code,id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result: PlaceResponse -> discoverHerePlaceHereDeveloperLiveData.setValue(result) })
                { error: Throwable? -> Log.e(TAG, "discoverHerePlacesHereDeveloper: ", error) }
    }

    companion object {
        private const val TAG = "ShowFeaturedPlaceVM"
    }
}