package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Results {
    @SerializedName("results")
    @Expose
    var results: List<Place>? = null
}