package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class DiscoverHereResponse {
    @SerializedName("search")
    @Expose
    private var search: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Search? = null

    @SerializedName("results")
    @Expose
    private var results: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Results? = null
    fun getSearch(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Search? {
        return search
    }

    fun setSearch(search: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Search?) {
        this.search = search
    }

    fun getResults(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Results? {
        return results
    }

    fun setResults(results: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Results?) {
        this.results = results
    }
}