package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class DiscoverExploreResponse {
    @SerializedName("results")
    @Expose
    private var results: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Results? = null

    @SerializedName("search")
    @Expose
    private var search: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Search? = null
    fun getResults(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Results? {
        return results
    }

    fun setResults(results: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Results?) {
        this.results = results
    }

    fun getSearch(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Search? {
        return search
    }

    fun setSearch(search: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Search?) {
        this.search = search
    }
}