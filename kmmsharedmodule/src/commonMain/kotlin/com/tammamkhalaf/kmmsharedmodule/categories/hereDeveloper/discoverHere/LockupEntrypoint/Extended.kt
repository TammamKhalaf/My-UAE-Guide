package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Extended {
    @SerializedName("openingHours")
    @Expose
    private var openingHours: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.OpeningHours? = null
    fun getOpeningHours(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.OpeningHours? {
        return openingHours
    }

    fun setOpeningHours(openingHours: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.OpeningHours?) {
        this.openingHours = openingHours
    }
}