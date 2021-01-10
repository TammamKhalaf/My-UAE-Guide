package com.tammamkhalaf.myuaeguide.categories.hotels.getAllPropertyInfoModel

class PropertyDescription {
    var clientToken: String? = null
    var address: Address? = null
    var priceMatchEnabled = false
    var name: String? = null
    var starRatingTitle: String? = null
    var starRating = 0.0
    var featuredPrice: FeaturedPrice? = null
    var mapWidget: MapWidget? = null
    var roomTypeNames: List<String>? = null
    var tagline: List<String>? = null
    var freebies: List<String>? = null
}