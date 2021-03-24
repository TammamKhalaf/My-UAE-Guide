package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Item_ {
    @SerializedName("count")
    @Expose
    private var count: Int? = null

    @SerializedName("average")
    @Expose
    private var average: Int? = null

    @SerializedName("via")
    @Expose
    private var via: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via_? = null

    @SerializedName("supplier")
    @Expose
    private var supplier: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier_? = null

    @SerializedName("attribution")
    @Expose
    private var attribution: String? = null
    fun getCount(): Int? {
        return count
    }

    fun setCount(count: Int?) {
        this.count = count
    }

    fun getAverage(): Int? {
        return average
    }

    fun setAverage(average: Int?) {
        this.average = average
    }

    fun getVia(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via_? {
        return via
    }

    fun setVia(via: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Via_?) {
        this.via = via
    }

    fun getSupplier(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier_? {
        return supplier
    }

    fun setSupplier(supplier: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Supplier_?) {
        this.supplier = supplier
    }

    fun getAttribution(): String? {
        return attribution
    }

    fun setAttribution(attribution: String?) {
        this.attribution = attribution
    }
}