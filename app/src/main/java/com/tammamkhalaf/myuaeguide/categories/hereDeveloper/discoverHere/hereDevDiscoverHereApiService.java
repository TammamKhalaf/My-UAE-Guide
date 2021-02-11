package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere;

import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.DiscoverExploreResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  hereDevDiscoverHereApiService {

    /**
     * https://places.sit.ls.hereapi.com/places/v1/discover/here
     * ?app_id={YOUR_APP_ID}
     * &app_code={YOUR_APP_CODE}&
     * at=52.50449,13.39091&pretty
     */
    @GET("places/v1/discover/around/")//todo change concepts from discover here to discover around AND
    Observable<DiscoverHereResponse> getHerePlaces(
            @Query("app_id") String app_id,//dmLgAQo631UJfwF5R2hH
            @Query("app_code") String app_code,//391hkRjz5Z3Ee1h3wz6Kng
            @Query("at") String position//or @Query("in")String circle_or_bounding_box);
           ,@Query("cat")List<String> cat_list);
}
