package com.tammamkhalaf.myuaeguide.Categories.NearbyPlaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Place {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null

    @SerializedName("website")
    @Expose
    var website: String? = null

    @SerializedName("location")
    @Expose
    var location: Location? = null

    @SerializedName("types")
    @Expose
    var types: List<String>? = null
    override fun toString(): String {
        return "Result{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", location=" + location +
                ", types=" + types +
                '}'
    }
}