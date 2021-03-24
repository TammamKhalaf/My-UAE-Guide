package com.tammamkhalaf.kmmsharedmodule.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse
import com.tammamkhalaf.myuaeguide.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FavPlacesViewModel @ViewModelInject constructor(private val repository: Repository) : ViewModel() {

    private lateinit var placesList: MutableLiveData<List<PlaceResponse>>

    private lateinit var favList: Single<MutableList<PlaceResponse>>


    fun getPlacesList(): Single<MutableList<PlaceResponse>>? {
        return repository.getPlaces()
    }

    @SuppressLint("CheckResult")
    fun getPlaces() {
        repository.getPlaces()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result -> placesList.setValue(result) }
                ) { error -> Log.e("viwModel", error.toString()) }
    }

    fun insertPlace(placeResponse: PlaceResponse) {
        repository.insertPlace(placeResponse)
    }

    fun deletePlace(placeResponse: PlaceResponse) {
        repository.deletePlace(placeResponse)
    }

    fun getFavPlaces() {
        favList = repository.getPlaces()!!
    }


}