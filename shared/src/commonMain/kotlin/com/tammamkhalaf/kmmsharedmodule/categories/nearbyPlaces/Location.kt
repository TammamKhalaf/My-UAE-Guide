package com.tammamkhalaf.kmmsharedmodule.categories.nearbyPlaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location(@SerializedName("lat")
               @Expose var lat: Double?, @SerializedName("lng")
               @Expose var lng: Double?)