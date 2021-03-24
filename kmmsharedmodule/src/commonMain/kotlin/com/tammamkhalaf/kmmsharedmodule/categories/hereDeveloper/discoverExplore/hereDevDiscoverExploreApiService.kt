package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore

import io.reactivex.rxjava3.core.Observable

interface hereDevDiscoverExploreApiService {
    /*,@Query("drilldown")Boolean optional,*/ /*@Query("cs")List<String> CommaSeparatedListOptional*/
    /**
     *
     * Resource
     * actions 	Report user interactions.
     * autosuggest 	Get autocomplete suggestions to build up search queries.
     * browse 	Find places matching specific categories around a location sorted by distance.
     * discover/explore 	Recommended places matching specific categories around a location.
     * discover/search 	Find places using a text query.
     * health 	Monitor the availability of the Places (Search) API
     * lookup 	Find a place using identifiers not originating from the Places (Search) API, for example PVIDs from HERE maps.
     *
     *
     * https://places.ls.hereapi.com/places/v1/
     * discover/search?apiKey={YOUR_API_KEY}&at=52.531,13.3843&q=Brandenburg+Gate
     */
    //@Headers("Accept-Language:ar-SA")
    @GET("places/v1/discover/explore/")
    fun getRecommendedPlaces(
            @Query("app_id") app_id: String?,  //dmLgAQo631UJfwF5R2hH
            @Query("app_code") app_code: String?,  //391hkRjz5Z3Ee1h3wz6Kng
            @Query("at") position: String?,  //or @Query("in")String circle_or_bounding_box,
            @Query("cat") cat_list: List<String?>?): Observable<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.DiscoverExploreResponse?>?

    companion object {
        const val AppID = "WBxqrkW7yaro8inXngMW"
        const val API_KEY = "a4js9KlwWF2fRUj2bdtPGCYWj49G9iyUDHohPaWR5VM"
        const val BASE_URL = "https://places.ls.hereapi.com"
        const val LEGACY_BASE_URL = "https://places.api.here.com"
        const val PATH = "/places/v1/"
    }
}