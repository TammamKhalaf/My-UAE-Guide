package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Results {
    @SerializedName("next")
    @Expose
    private var next: String? = null

    @SerializedName("items")
    @Expose
    private var items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Item>? = null
    fun getNext(): String? {
        return next
    }

    fun setNext(next: String?) {
        this.next = next
    }

    fun getItems(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Item>? {
        return items
    }

    fun setItems(items: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Item>?) {
        this.items = items
    }
}