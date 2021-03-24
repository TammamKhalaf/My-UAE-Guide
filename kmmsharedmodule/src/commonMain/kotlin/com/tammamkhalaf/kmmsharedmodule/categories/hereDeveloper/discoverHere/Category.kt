package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Category {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("href")
    @Expose
    private var href: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null
    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
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
}