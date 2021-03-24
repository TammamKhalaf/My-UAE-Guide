package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Results {
    @SerializedName("items")
    @Expose
    private var items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Item>? = null
    fun getItems(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Item>? {
        return items
    }

    fun setItems(items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Item>?) {
        this.items = items
    }
}