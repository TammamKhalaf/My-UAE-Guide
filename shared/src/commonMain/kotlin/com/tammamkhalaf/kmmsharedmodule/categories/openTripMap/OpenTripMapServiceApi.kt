package com.tammamkhalaf.myuaeguide.categories.openTripMap

import io.reactivex.rxjava3.core.Observable

interface OpenTripMapServiceApi {
    //Geographic coordinates of populated place
    /**
     * Geographic coordinates of populated place
     *
     *
     * Returns geographic coordinates for the given placename (region, city, village, etc.).
     * The method returns the place whose name is most similar to the search string.
     * Service based on GeoNames database.
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/geoname")
    fun getGeoName(@Path("lang") lang: String?,  //en or ru
                   @Query("name") placeName: String?,
                   @Query("country") TwoChar: String?,
                   @Query("apikey") apiKey: String?): Observable<com.tammamkhalaf.myuaeguide.categories.openTripMap.Geoname?>?

    /**
     *
     * Objects list
     *
     * Method returns all objects (or number of objects) in the given boundary box optionally filtered
     * by parameters.
     * Only basic information is include in response: xid, name, kinds, osm, wikidata and geometry
     * of each object.
     * Depending on the chosen format, the response is either a simple array of objects
     * (with a smaller volume)
     * or an object in GeoJSON format.
     *
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/bbox")
    fun getAllPlaceInBBox(@Path("lang") lang: String?,
                          @Query("lon_min") lon_min: Double?,
                          @Query("lon_max") lon_max: Double?,
                          @Query("lat_min") lat_min: Double?,
                          @Query("lat_max") lat_max: Double?,
                          @Query("src_geom") osm_: String?,
                          @Query("src_attr") osm: String?,
                          @Query("kinds") type: String?,
                          @Query("rate") rate: Int,
                          @Query("format") json: String?,
                          @Query("limit") limit_max_500: Int,
                          @Query("apikey") apiKey: String?): Observable<java.util.ArrayList<com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleFeature?>?>?

    /**
     *
     * Method returns objects closest to the selected point optionally filtered by parameters.
     * Only basic information is include in response: xid, name, kinds, osm, wikidata and geometry of each
     * object.
     * Depending on the chosen format, the response is either a simple array of object
     * s (with a smaller volume)
     * or an object in GeoJSON format.
     *
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/radius")
    fun getAllPlacesClosestToPoint(
            @Query("radius") radius: Int,
            @Query("lon") lon: Double?,
            @Query("lat") lat: Double?,
            @Query("src_geom") _osm: String?,
            @Query("src_attr") osm: String?,
            @Query("kinds") type: String?,
            @Query("format") json: String?,
            @Query("limit") limit: Int,
            @Query("apikey") API_KEY: String?
    ): Observable<java.util.ArrayList<com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleFeature?>?>?

    /**
     *
     * Method returns suggestions for search term closest to the selected point optionally filtered by
     * parameters.
     * Only basic information is include in response: xid, name, kinds, osm, wikidata of each object.
     * Depending on the chosen format, the response is either a simple array of objects
     * (with a smaller volume) or an object in GeoJSON format.
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/autosuggest")
    fun getSuggestionsClosestToPoint(
            @Query("radius") radius: Int,
            @Query("lon") lon: Double?,
            @Query("lat") lat: Double?,
            @Query("src_geom") _osm: String?,
            @Query("src_attr") osm: String?,
            @Query("kinds") type: String?,
            @Query("rate") rate: String?,
            @Query("format") json: String?,
            @Query("props") base_or_address: String?,
            @Query("limit") limit: Int,
            @Query("apikey") API_KEY: String?
    ): Observable<java.util.ArrayList<com.tammamkhalaf.myuaeguide.categories.openTripMap.SimpleSuggestFeature?>?>?

    /**
     * Object properties
     *
     * Returns detailed information about the object. Objects can contain different amount of information.
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/xid/{xid}")
    fun getDetailedInfoAboutPlace(@Path("lang") lang: String?, @Path("xid") placeId: String?, @Query("apikey") API_KEY: String?): Observable<com.tammamkhalaf.myuaeguide.categories.openTripMap.Places?>?

    companion object {
        const val BASE_URL = "https://api.opentripmap.com/0.1/"
        const val API_KEY = "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed"
    }
}