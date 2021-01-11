package com.tammamkhalaf.myuaeguide.categories.openTripMap;

import java.util.ArrayList;
import java.util.List;

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
     * The method returns the place whose name is most similar to the search string.
     * Service based on GeoNames database.
     */

    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/geoname")
    Observable<Geoname> getGeoName(@Path("lang")String lang,//en or ru
                                                            @Query("name")String placeName,
                                                            @Query("country")String TwoChar,
                                                            @Query("apikey")String apiKey);

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
    Observable<ArrayList<SimpleFeature>> getAllPlaceInBBox(@Path("lang")String lang,
                                                        @Query("lon_min")Double lon_min,
                                                      @Query("lon_max")Double lon_max,
                                                      @Query("lat_min")Double lat_min,
                                                      @Query("lat_max")Double lat_max,
                                                      @Query("src_geom")String osm_,
                                                      @Query("src_attr")String osm,
                                                      @Query("kinds")String type,
                                                      @Query("rate")int rate,
                                                      @Query("format")String json,
                                                      @Query("limit")int limit_max_500,
                                                      @Query("apikey")String apiKey);

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
    Observable<ArrayList<SimpleFeature>> getAllPlacesClosestToPoint(
            @Query("radius")int radius,
            @Query("lon")Double lon,
            @Query("lat")Double lat,
            @Query("src_geom")String _osm,
            @Query("src_attr")String osm,
            @Query("kinds")String type,
            @Query("format")String json,
            @Query("limit") int limit,
            @Query("apikey")String API_KEY
    );

    /**
     *
     *Method returns suggestions for search term closest to the selected point optionally filtered by
     *  parameters.
     *  Only basic information is include in response: xid, name, kinds, osm, wikidata of each object.
     *  Depending on the chosen format, the response is either a simple array of objects
     *  (with a smaller volume) or an object in GeoJSON format.
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/autosuggest")
    Observable<ArrayList<SimpleSuggestFeature>> getSuggestionsClosestToPoint(
            @Query("radius")int radius,
            @Query("lon")Double lon,
            @Query("lat")Double lat,
            @Query("src_geom")String _osm,
            @Query("src_attr")String osm,
            @Query("kinds")String type,
            @Query("rate")String rate,
            @Query("format")String json,
            @Query("props")String base_or_address,
            @Query("limit") int limit,
            @Query("apikey")String API_KEY
    );

    /**
     * Object properties
     *
     * Returns detailed information about the object. Objects can contain different amount of information.
     */
    @Headers("content-type:application/json;charset=utf-8")
    @GET("{lang}/places/xid/{xid}")
    Observable<Places> getDetailedInfoAboutPlace(@Path("lang")String lang,@Path("xid")String placeId,@Query("apikey")String API_KEY);

}
