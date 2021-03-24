package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import com.google.gson.annotations.Expose

class Tag {
    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("group")
    @Expose
    private var group: String? = null
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

    fun getGroup(): String? {
        return group
    }

    fun setGroup(group: String?) {
        this.group = group
    }
}