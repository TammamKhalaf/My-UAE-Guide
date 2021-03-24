package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Places {
    @SerializedName("kinds")
    @Expose
    private var kinds: String? = null

    @SerializedName("sources")
    @Expose
    private var sources: com.tammamkhalaf.myuaeguide.categories.openTripMap.Sources? = null

    @SerializedName("bbox")
    @Expose
    private var bbox: com.tammamkhalaf.myuaeguide.categories.openTripMap.Bbox? = null

    @SerializedName("point")
    @Expose
    private var point: com.tammamkhalaf.myuaeguide.categories.openTripMap.Point? = null

    @SerializedName("osm")
    @Expose
    private var osm: String? = null

    @SerializedName("otm")
    @Expose
    private var otm: String? = null

    @SerializedName("xid")
    @Expose
    private var xid: String? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("wikipedia")
    @Expose
    private var wikipedia: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("wikidata")
    @Expose
    private var wikidata: String? = null

    @SerializedName("rate")
    @Expose
    private var rate: String? = null

    @SerializedName("info")
    @Expose
    private var info: com.tammamkhalaf.myuaeguide.categories.openTripMap.Info? = null
    fun getKinds(): String? {
        return kinds
    }

    fun setKinds(kinds: String?) {
        this.kinds = kinds
    }

    fun getSources(): com.tammamkhalaf.myuaeguide.categories.openTripMap.Sources? {
        return sources
    }

    fun setSources(sources: com.tammamkhalaf.myuaeguide.categories.openTripMap.Sources?) {
        this.sources = sources
    }

    fun getBbox(): com.tammamkhalaf.myuaeguide.categories.openTripMap.Bbox? {
        return bbox
    }

    fun setBbox(bbox: com.tammamkhalaf.myuaeguide.categories.openTripMap.Bbox?) {
        this.bbox = bbox
    }

    fun getPoint(): com.tammamkhalaf.myuaeguide.categories.openTripMap.Point? {
        return point
    }

    fun setPoint(point: com.tammamkhalaf.myuaeguide.categories.openTripMap.Point?) {
        this.point = point
    }

    fun getOsm(): String? {
        return osm
    }

    fun setOsm(osm: String?) {
        this.osm = osm
    }

    fun getOtm(): String? {
        return otm
    }

    fun setOtm(otm: String?) {
        this.otm = otm
    }

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

    fun getWikipedia(): String? {
        return wikipedia
    }

    fun setWikipedia(wikipedia: String?) {
        this.wikipedia = wikipedia
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getWikidata(): String? {
        return wikidata
    }

    fun setWikidata(wikidata: String?) {
        this.wikidata = wikidata
    }

    fun getRate(): String? {
        return rate
    }

    fun setRate(rate: String?) {
        this.rate = rate
    }

    fun getInfo(): com.tammamkhalaf.myuaeguide.categories.openTripMap.Info? {
        return info
    }

    fun setInfo(info: com.tammamkhalaf.myuaeguide.categories.openTripMap.Info?) {
        this.info = info
    }
}