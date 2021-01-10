package com.tammamkhalaf.myuaeguide.categories.hotels.listPropertiesModel

import com.google.gson.annotations.SerializedName

class AdditionalUrlParams {
    @SerializedName("resolved-location")
    var resolvedLocation: String? = null

    @SerializedName("q-destination")
    var qDestination: String? = null

    @SerializedName("destination-id")
    var destinationId: String? = null
}