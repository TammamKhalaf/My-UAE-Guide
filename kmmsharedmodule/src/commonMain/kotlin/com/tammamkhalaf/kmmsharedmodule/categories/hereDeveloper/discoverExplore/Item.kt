package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

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
    private var averageRating: Double? = null

    @SerializedName("category")
    @Expose
    private var category: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Category? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    @SerializedName("vicinity")
    @Expose
    private var vicinity: String? = null

    @SerializedName("having")
    @Expose
    private var having: List<Any>? = null

    @SerializedName("type")
    @Expose
    private var type: String? = null

    @SerializedName("href")
    @Expose
    private var href: String? = null

    @SerializedName("id")
    @Expose
    private var id: String? = null

    @SerializedName("alternativeNames")
    @Expose
    private var alternativeNames: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.AlternativeName>? = null

    @SerializedName("tags")
    @Expose
    private var tags: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Tag>? = null

    @SerializedName("openingHours")
    @Expose
    private var openingHours: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.OpeningHours? = null

    @SerializedName("chainIds")
    @Expose
    private var chainIds: List<String>? = null
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

    fun getAverageRating(): Double? {
        return averageRating
    }

    fun setAverageRating(averageRating: Double?) {
        this.averageRating = averageRating
    }

    fun getCategory(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Category? {
        return category
    }

    fun setCategory(category: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Category?) {
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

    fun getHaving(): List<Any>? {
        return having
    }

    fun setHaving(having: List<Any>?) {
        this.having = having
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

    fun getAlternativeNames(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.AlternativeName>? {
        return alternativeNames
    }

    fun setAlternativeNames(alternativeNames: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.AlternativeName>?) {
        this.alternativeNames = alternativeNames
    }

    fun getTags(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Tag>? {
        return tags
    }

    fun setTags(tags: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.Tag>?) {
        this.tags = tags
    }

    fun getOpeningHours(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.OpeningHours? {
        return openingHours
    }

    fun setOpeningHours(openingHours: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.OpeningHours?) {
        this.openingHours = openingHours
    }

    fun getChainIds(): List<String>? {
        return chainIds
    }

    fun setChainIds(chainIds: List<String>?) {
        this.chainIds = chainIds
    }
}