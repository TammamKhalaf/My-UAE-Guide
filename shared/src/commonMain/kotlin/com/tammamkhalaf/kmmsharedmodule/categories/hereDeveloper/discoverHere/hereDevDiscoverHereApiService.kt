package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere

interface hereDevDiscoverHereApiService {
    /**
     * https://places.sit.ls.hereapi.com/places/v1/discover/here
     * ?app_id={YOUR_APP_ID}
     * &app_code={YOUR_APP_CODE}&
     * at=52.50449,13.39091&pretty
     */
    @GET("places/v1/discover/around/")
    fun  //todo change concepts from discover here to discover around AND
            getHerePlaces(
            @Query("app_id") app_id: String?,  //dmLgAQo631UJfwF5R2hH
            @Query("app_code") app_code: String?,  //391hkRjz5Z3Ee1h3wz6Kng
            @Query("at") position: String? //or @Query("in")String circle_or_bounding_box);
            , @Query("cat") cat_list: List<String?>?): Observable<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.DiscoverHereResponse?>?
}