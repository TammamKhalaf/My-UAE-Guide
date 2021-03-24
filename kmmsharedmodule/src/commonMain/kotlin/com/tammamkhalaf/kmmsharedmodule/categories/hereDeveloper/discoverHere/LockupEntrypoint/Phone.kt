package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Phone {
    @SerializedName("value")
    @Expose
    private var value: String? = null

    @SerializedName("label")
    @Expose
    private var label: String? = null
    fun getValue(): String? {
        return value
    }

    fun setValue(value: String?) {
        this.value = value
    }

    fun getLabel(): String? {
        return label
    }

    fun setLabel(label: String?) {
        this.label = label
    }
}