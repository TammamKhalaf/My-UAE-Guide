package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Reviews {
    @SerializedName("available")
    @Expose
    private var available: Int? = null

    @SerializedName("items")
    @Expose
    private var items: List<Any>? = null
    fun getAvailable(): Int? {
        return available
    }

    fun setAvailable(available: Int?) {
        this.available = available
    }

    fun getItems(): List<Any>? {
        return items
    }

    fun setItems(items: List<Any>?) {
        this.items = items
    }
}