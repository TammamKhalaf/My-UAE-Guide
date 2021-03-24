package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Location {
    @SerializedName("position")
    @Expose
    private var position: List<Double>? = null

    @SerializedName("address")
    @Expose
    private var address: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Address? = null
    fun getPosition(): List<Double>? {
        return position
    }

    fun setPosition(position: List<Double>?) {
        this.position = position
    }

    fun getAddress(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Address? {
        return address
    }

    fun setAddress(address: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Address?) {
        this.address = address
    }
}