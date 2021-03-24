package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Point {
    @SerializedName("lon")
    @Expose
    private var lon: Double? = null

    @SerializedName("lat")
    @Expose
    private var lat: Double? = null
    fun getLon(): Double? {
        return lon
    }

    fun setLon(lon: Double?) {
        this.lon = lon
    }

    fun getLat(): Double? {
        return lat
    }

    fun setLat(lat: Double?) {
        this.lat = lat
    }
}