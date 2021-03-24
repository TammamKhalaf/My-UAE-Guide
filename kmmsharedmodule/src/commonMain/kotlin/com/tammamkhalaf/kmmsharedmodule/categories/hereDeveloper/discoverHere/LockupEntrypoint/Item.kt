package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Item {
    @SerializedName("src")
    @Expose
    private var src: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("href")
    @Expose
    private var href: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("date")
    @Expose
    private var date: String? = null

    @SerializedName("supports")
    @Expose
    private var supports: List<String>? = null

    @SerializedName("user")
    @Expose
    private var user: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.User? = null

    @SerializedName("via")
    @Expose
    private var via: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via? = null

    @SerializedName("supplier")
    @Expose
    private var supplier: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier? = null

    @SerializedName("attribution")
    @Expose
    private var attribution: String? = null
    fun getSrc(): String? {
        return src
    }

    fun setSrc(src: String?) {
        this.src = src
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

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

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getSupports(): List<String>? {
        return supports
    }

    fun setSupports(supports: List<String>?) {
        this.supports = supports
    }

    fun getUser(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.User? {
        return user
    }

    fun setUser(user: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.User?) {
        this.user = user
    }

    fun getVia(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via? {
        return via
    }

    fun setVia(via: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via?) {
        this.via = via
    }

    fun getSupplier(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier? {
        return supplier
    }

    fun setSupplier(supplier: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier?) {
        this.supplier = supplier
    }

    fun getAttribution(): String? {
        return attribution
    }

    fun setAttribution(attribution: String?) {
        this.attribution = attribution
    }
}