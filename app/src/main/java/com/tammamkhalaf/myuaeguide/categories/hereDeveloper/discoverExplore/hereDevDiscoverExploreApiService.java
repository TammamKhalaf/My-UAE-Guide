package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface hereDevDiscoverExploreApiService {
   String  AppID = "WBxqrkW7yaro8inXngMW";

   String API_KEY = "a4js9KlwWF2fRUj2bdtPGCYWj49G9iyUDHohPaWR5VM";

   String BASE_URL = "https://places.ls.hereapi.com";

   String LEGACY_BASE_URL = "https://places.api.here.com";

   String PATH = "/places/v1/";


    /*,@Query("drilldown")Boolean optional,*/
    /*@Query("cs")List<String> CommaSeparatedListOptional*/

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
     * */
    //@Headers("Accept-Language:ar-SA")
    @GET("places/v1/discover/explore/")
    Observable<DiscoverExploreResponse> getRecommendedPlaces(
            @Query("app_id")String app_id,//dmLgAQo631UJfwF5R2hH
            @Query("app_code")String app_code,//391hkRjz5Z3Ee1h3wz6Kng
            @Query("at")String position,//or @Query("in")String circle_or_bounding_box,
            @Query("cat")List<String> cat_list);
}
