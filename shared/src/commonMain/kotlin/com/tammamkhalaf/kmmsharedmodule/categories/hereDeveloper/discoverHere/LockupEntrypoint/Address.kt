package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import com.google.gson.annotations.Expose

class Address {
    @SerializedName("text")
    @Expose
    private var text: String? = null

    @SerializedName("house")
    @Expose
    private var house: String? = null

    @SerializedName("street")
    @Expose
    private var street: String? = null

    @SerializedName("postalCode")
    @Expose
    private var postalCode: String? = null

    @SerializedName("district")
    @Expose
    private var district: String? = null

    @SerializedName("city")
    @Expose
    private var city: String? = null

    @SerializedName("county")
    @Expose
    private var county: String? = null

    @SerializedName("state")
    @Expose
    private var state: String? = null

    @SerializedName("country")
    @Expose
    private var country: String? = null

    @SerializedName("countryCode")
    @Expose
    private var countryCode: String? = null
    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text
    }

    fun getHouse(): String? {
        return house
    }

    fun setHouse(house: String?) {
        this.house = house
    }

    fun getStreet(): String? {
        return street
    }

    fun setStreet(street: String?) {
        this.street = street
    }

    fun getPostalCode(): String? {
        return postalCode
    }

    fun setPostalCode(postalCode: String?) {
        this.postalCode = postalCode
    }

    fun getDistrict(): String? {
        return district
    }

    fun setDistrict(district: String?) {
        this.district = district
    }

    fun getCity(): String? {
        return city
    }

    fun setCity(city: String?) {
        this.city = city
    }

    fun getCounty(): String? {
        return county
    }

    fun setCounty(county: String?) {
        this.county = county
    }

    fun getState(): String? {
        return state
    }

    fun setState(state: String?) {
        this.state = state
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getCountryCode(): String? {
        return countryCode
    }

    fun setCountryCode(countryCode: String?) {
        this.countryCode = countryCode
    }
}