package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Location {
    @SerializedName("position")
    @Expose
    private var position: List<Double>? = null

    @SerializedName("address")
    @Expose
    private var address: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Address? = null

    @SerializedName("access")
    @Expose
    private var access: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Access>? = null
    fun getPosition(): List<Double>? {
        return position
    }

    fun setPosition(position: List<Double>?) {
        this.position = position
    }

    fun getAddress(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Address? {
        return address
    }

    fun setAddress(address: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Address?) {
        this.address = address
    }

    fun getAccess(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Access>? {
        return access
    }

    fun setAccess(access: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Access>?) {
        this.access = access
    }
}