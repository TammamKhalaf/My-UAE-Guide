package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Search {
    @SerializedName("context")
    @Expose
    private var context: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Context? = null
    fun getContext(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Context? {
        return context
    }

    fun setContext(context: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Context?) {
        this.context = context
    }
}