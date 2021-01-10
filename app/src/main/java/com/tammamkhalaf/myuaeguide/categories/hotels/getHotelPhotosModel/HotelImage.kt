package com.tammamkhalaf.myuaeguide.categories.hotels.getHotelPhotosModel

import com.tammamkhalaf.myuaeguide.categories.hotels.getAllPropertyInfoModel.Size
import com.tammamkhalaf.myuaeguide.categories.hotels.getAllPropertyInfoModel.TrackingDetails

class HotelImage {
    var baseUrl: String? = null
    var imageId = 0
    var mediaGUID: String? = null
    var sizes: List<Size>? = null
    var trackingDetails: TrackingDetails? = null
}