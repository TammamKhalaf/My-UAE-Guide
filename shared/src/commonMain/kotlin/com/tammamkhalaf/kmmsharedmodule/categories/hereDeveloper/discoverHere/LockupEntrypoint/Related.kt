package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Related {
    @SerializedName("recommended")
    @Expose
    private var recommended: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Recommended? = null

    @SerializedName("public-transport")
    @Expose
    private var publicTransport: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PublicTransport? = null
    fun getRecommended(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Recommended? {
        return recommended
    }

    fun setRecommended(recommended: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Recommended?) {
        this.recommended = recommended
    }

    fun getPublicTransport(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PublicTransport? {
        return publicTransport
    }

    fun setPublicTransport(publicTransport: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PublicTransport?) {
        this.publicTransport = publicTransport
    }
}