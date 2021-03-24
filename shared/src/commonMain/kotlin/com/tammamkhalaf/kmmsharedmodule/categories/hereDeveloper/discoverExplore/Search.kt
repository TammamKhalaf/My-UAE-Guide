package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Search {
    @SerializedName("context")
    @Expose
    private var context: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Context? = null
    fun getContext(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Context? {
        return context
    }

    fun setContext(context: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Context?) {
        this.context = context
    }
}