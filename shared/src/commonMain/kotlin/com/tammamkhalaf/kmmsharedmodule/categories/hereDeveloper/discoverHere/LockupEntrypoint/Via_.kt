package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Via_ {
    @SerializedName("href")
    @Expose
    private var href: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null
    fun getHref(): String? {
        return href
    }

    fun setHref(href: String?) {
        this.href = href
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }
}