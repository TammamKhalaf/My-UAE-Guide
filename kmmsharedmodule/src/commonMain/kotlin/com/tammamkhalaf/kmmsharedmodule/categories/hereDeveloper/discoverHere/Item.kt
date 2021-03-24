package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

import com.google.gson.annotations.Expose

class Item {
    @SerializedName("position")
    @Expose
    private var position: List<Double>? = null

    @SerializedName("distance")
    @Expose
    private var distance: Int? = null

    @SerializedName("title")
    @Expose
    private var title: String? = null

    @SerializedName("averageRating")
    @Expose
    private var averageRating: Int? = null

    @SerializedName("category")
    @Expose
    private var category: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Category? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    @SerializedName("vicinity")
    @Expose
    private var vicinity: String? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("href")
    @Expose
    private var href: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null
    fun getPosition(): List<Double>? {
        return position
    }

    fun setPosition(position: List<Double>?) {
        this.position = position
    }

    fun getDistance(): Int? {
        return distance
    }

    fun setDistance(distance: Int?) {
        this.distance = distance
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getAverageRating(): Int? {
        return averageRating
    }

    fun setAverageRating(averageRating: Int?) {
        this.averageRating = averageRating
    }

    fun getCategory(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Category? {
        return category
    }

    fun setCategory(category: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.Category?) {
        this.category = category
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    fun getVicinity(): String? {
        return vicinity
    }

    fun setVicinity(vicinity: String?) {
        this.vicinity = vicinity
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun getHref(): String? {
        return href
    }

    fun setHref(href: String?) {
        this.href = href
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }
}