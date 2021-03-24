package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Access {
    @SerializedName("position")
    @Expose
    private var position: List<Double>? = null

    @SerializedName("accessType")
    @Expose
    private var accessType: String? = null
    fun getPosition(): List<Double>? {
        return position
    }

    fun setPosition(position: List<Double>?) {
        this.position = position
    }

    fun getAccessType(): String? {
        return accessType
    }

    fun setAccessType(accessType: String?) {
        this.accessType = accessType
    }
}