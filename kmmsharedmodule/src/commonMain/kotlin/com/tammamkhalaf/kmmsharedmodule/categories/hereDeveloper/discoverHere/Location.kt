package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Location {
    @SerializedName("position")
    @Expose
    private var position: List<String>? = null
    fun getPosition(): List<String>? {
        return position
    }

    fun setPosition(position: List<String>?) {
        this.position = position
    }
}