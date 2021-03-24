package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Geoname {
    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("country")
    @Expose
    private var country: String? = null

    @SerializedName("lat")
    @Expose
    private var lat: Double? = null

    @SerializedName("lon")
    @Expose
    private var lon: Double? = null

    @SerializedName("population")
    @Expose
    private var population: Int? = null

    @SerializedName("timezone")
    @Expose
    private var timezone: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getLat(): Double? {
        return lat
    }

    fun setLat(lat: Double?) {
        this.lat = lat
    }

    fun getLon(): Double? {
        return lon
    }

    fun setLon(lon: Double?) {
        this.lon = lon
    }

    fun getPopulation(): Int? {
        return population
    }

    fun setPopulation(population: Int?) {
        this.population = population
    }

    fun getTimezone(): String? {
        return timezone
    }

    fun setTimezone(timezone: String?) {
        this.timezone = timezone
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}