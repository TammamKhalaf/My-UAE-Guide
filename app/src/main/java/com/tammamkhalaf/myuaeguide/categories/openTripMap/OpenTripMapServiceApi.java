package com.tammamkhalaf.myuaeguide.categories.openTripMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenTripMapServiceApi {
    String BASE_URL = "https://api.opentripmap.com/0.1/";
    String API_KEY = "5ae2e3f221c38a28845f05b64293a0f5d790db9d3aaf49fbb0ae5aed";

    //Geographic coordinates of populated place
    /**
     * Geographic coordinates of populated place
     *
     *
     * Returns geographic coordinates for the given placename (region, city, village, etc.).
     * The method returns the place whose name is most similar to the search string. Service based on GeoNames database.
     */

    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/geoname")
    Observable<Geoname> getGeographicCoordinatesOfPlaceName(@Path("lang")String lang,//en or ru
                                                            @Query("name")String placeName,
                                                            @Query("country")String TwoChar,
                                                            @Query("apikey")String apiKey);


    /**
     *
     * Objects list
     *
     * Method returns all objects (or number of objects) in the given boundary box optionally filtered by parameters.
     * Only basic information is include in response: xid, name, kinds, osm, wikidata and geometry of each object.
     * Depending on the chosen format, the response is either a simple array of objects (with a smaller volume)
     * or an object in GeoJSON format.
     *
     */

    @GET("{lang}/places/bbox")
    Observable<SimpleFeature>

    /**
     *
     * Method returns objects closest to the selected point optionally filtered by parameters.
     * Only basic information is include in response: xid, name, kinds, osm, wikidata and geometry of each object.
     * Depending on the chosen format, the response is either a simple array of objects (with a smaller volume)
     * or an object in GeoJSON format.
     *
     */
    @GET("{lang}/places/radius")
    Observable<SimpleFeature>

    /**
     *
     *Method returns suggestions for search term closest to the selected point optionally filtered by parameters.
     *  Only basic information is include in response: xid, name, kinds, osm, wikidata of each object.
     *  Depending on the chosen format, the response is either a simple array of objects (with a smaller volume) or an object in GeoJSON format.
     */
    @GET("{lang}/places/autosuggest")
    Observable<SimpleSuggestFeature>

    /**
     * Object properties
     *
     * Returns detailed information about the object. Objects can contain different amount of information.
     */
    @GET("{lang}/places/xid/{xid}")
    Observable<Places>

}
