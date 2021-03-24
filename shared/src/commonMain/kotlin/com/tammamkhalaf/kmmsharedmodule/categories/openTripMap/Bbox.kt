package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Bbox {
    @SerializedName("lat_max")
    @Expose
    private var latMax: Double? = null

    @SerializedName("lat_min")
    @Expose
    private var latMin: Double? = null

    @SerializedName("lon_max")
    @Expose
    private var lonMax: Double? = null

    @SerializedName("lon_min")
    @Expose
    private var lonMin: Double? = null
    fun getLatMax(): Double? {
        return latMax
    }

    fun setLatMax(latMax: Double?) {
        this.latMax = latMax
    }

    fun getLatMin(): Double? {
        return latMin
    }

    fun setLatMin(latMin: Double?) {
        this.latMin = latMin
    }

    fun getLonMax(): Double? {
        return lonMax
    }

    fun setLonMax(lonMax: Double?) {
        this.lonMax = lonMax
    }

    fun getLonMin(): Double? {
        return lonMin
    }

    fun setLonMin(lonMin: Double?) {
        this.lonMin = lonMin
    }
}