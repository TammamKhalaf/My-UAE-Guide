package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class SimpleSuggestFeature {
    @SerializedName("xid")
    @Expose
    private var xid: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("highlighted_name")
    @Expose
    private var highlightedName: String? = null

    @SerializedName("dist")
    @Expose
    private var dist: Double? = null

    @SerializedName("rate")
    @Expose
    private var rate: Int? = null

    @SerializedName("osm")
    @Expose
    private var osm: String? = null

    @SerializedName("kinds")
    @Expose
    private var kinds: String? = null

    @SerializedName("point")
    @Expose
    private var point: com.tammamkhalaf.myuaeguide.categories.openTripMap.Point? = null

    @SerializedName("wikidata")
    @Expose
    private var wikidata: String? = null
    fun getXid(): String? {
        return xid
    }

    fun setXid(xid: String?) {
        this.xid = xid
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getHighlightedName(): String? {
        return highlightedName
    }

    fun setHighlightedName(highlightedName: String?) {
        this.highlightedName = highlightedName
    }

    fun getDist(): Double? {
        return dist
    }

    fun setDist(dist: Double?) {
        this.dist = dist
    }

    fun getRate(): Int? {
        return rate
    }

    fun setRate(rate: Int?) {
        this.rate = rate
    }

    fun getOsm(): String? {
        return osm
    }

    fun setOsm(osm: String?) {
        this.osm = osm
    }

    fun getKinds(): String? {
        return kinds
    }

    fun setKinds(kinds: String?) {
        this.kinds = kinds
    }

    fun getPoint(): com.tammamkhalaf.myuaeguide.categories.openTripMap.Point? {
        return point
    }

    fun setPoint(point: com.tammamkhalaf.myuaeguide.categories.openTripMap.Point?) {
        this.point = point
    }

    fun getWikidata(): String? {
        return wikidata
    }

    fun setWikidata(wikidata: String?) {
        this.wikidata = wikidata
    }
}