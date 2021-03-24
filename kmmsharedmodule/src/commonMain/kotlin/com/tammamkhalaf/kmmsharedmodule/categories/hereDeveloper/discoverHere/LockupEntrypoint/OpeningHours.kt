package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class OpeningHours {
    @SerializedName("text")
    @Expose
    private var text: String? = null

    @SerializedName("label")
    @Expose
    private var label: String? = null

    @SerializedName("isOpen")
    @Expose
    private var isOpen: Boolean? = null

    @SerializedName("structured")
    @Expose
    private var structured: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Structured>? = null
    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun getLabel(): String? {
        return label
    }

    fun setLabel(label: String?) {
        this.label = label
    }

    fun getIsOpen(): Boolean? {
        return isOpen
    }

    fun setIsOpen(isOpen: Boolean?) {
        this.isOpen = isOpen
    }

    fun getStructured(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Structured>? {
        return structured
    }

    fun setStructured(structured: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Structured>?) {
        this.structured = structured
    }
}