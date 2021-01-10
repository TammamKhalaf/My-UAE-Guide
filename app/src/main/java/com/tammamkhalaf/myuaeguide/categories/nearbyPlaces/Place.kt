package com.tammamkhalaf.myuaeguide.categories.nearbyPlaces

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Place(@SerializedName("id")
            @Expose var id: String?, @SerializedName("name")
            @Expose var name: String?, @SerializedName("address")
            @Expose var address: String?, @SerializedName("phone_number")
            @Expose var phoneNumber: String?, @SerializedName("website")
            @Expose var website: String?, @SerializedName("location")
            @Expose var location: Location?, @SerializedName("types")
            @Expose var types: List<String>?) {


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