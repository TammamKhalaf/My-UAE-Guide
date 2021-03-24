package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Sources {
    @SerializedName("geometry")
    @Expose
    private var geometry: String? = null

    @SerializedName("attributes")
    @Expose
    private var attributes: List<String>? = null
    fun getGeometry(): String? {
        return geometry
    }

    fun setGeometry(geometry: String?) {
        this.geometry = geometry
    }

    fun getAttributes(): List<String>? {
        return attributes
    }

    fun setAttributes(attributes: List<String>?) {
        this.attributes = attributes
    }
}