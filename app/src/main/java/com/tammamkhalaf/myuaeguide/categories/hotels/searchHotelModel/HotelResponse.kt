package com.tammamkhalaf.myuaeguide.categories.hotels.searchHotelModel

class HotelResponse {
    var term: String? = null
    var moresuggestions = 0
    var autoSuggestInstance: Any? = null
    var trackingID: String? = null
    var misspellingfallback = false
    var suggestions: List<Suggestion>? = null
}