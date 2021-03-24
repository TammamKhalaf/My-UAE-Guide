package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Images {
    @SerializedName("available")
    @Expose
    private var available: Int? = null

    @SerializedName("items")
    @Expose
    private var items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item>? = null
    fun getAvailable(): Int? {
        return available
    }

    fun setAvailable(available: Int?) {
        this.available = available
    }

    fun getItems(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item>? {
        return items
    }

    fun setItems(items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Item>?) {
        this.items = items
    }
}