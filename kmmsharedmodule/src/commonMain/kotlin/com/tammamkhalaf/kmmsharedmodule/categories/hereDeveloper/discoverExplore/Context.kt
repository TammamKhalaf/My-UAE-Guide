package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Context {
    @SerializedName("location")
    @Expose
    private var location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Location? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("href")
    @Expose
    private var href: String? = null
    fun getLocation(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Location? {
        return location
    }

    fun setLocation(location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Location?) {
        this.location = location
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getHref(): String? {
        return href
    }

    fun setHref(href: String?) {
        this.href = href
    }
}