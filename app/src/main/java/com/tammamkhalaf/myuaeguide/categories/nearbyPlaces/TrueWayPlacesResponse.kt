package com.tammamkhalaf.myuaeguide.categories.nearbyPlaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrueWayPlacesResponse(@SerializedName("results")
              @Expose var results: List<Place>?) {

}