package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Context {
    @SerializedName("location")
    @Expose
    private var location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Location? = null
    fun getLocation(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Location? {
        return location
    }

    fun setLocation(location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Location?) {
        this.location = location
    }
}