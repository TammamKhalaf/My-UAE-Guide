package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface hereDevLockupApiService {


    @GET("places/v1/places/lookup/")
    Observable<PlaceResponse> lookupPlace(@Query("app_id")String app_id,
                                          @Query("app_code")String app_code,
                                          @Query("source") String pvid,
                                          @Query("id")String id);
}
