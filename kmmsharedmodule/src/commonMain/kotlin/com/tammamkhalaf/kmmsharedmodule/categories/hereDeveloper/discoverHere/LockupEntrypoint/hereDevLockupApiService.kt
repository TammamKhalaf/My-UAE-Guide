package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import io.reactivex.rxjava3.core.Observable

interface hereDevLockupApiService {
    @GET("places/v1/places/lookup/")
    fun lookupPlace(@Query("app_id") app_id: String?,
                    @Query("app_code") app_code: String?,
                    @Query("source") pvid: String?,
                    @Query("id") id: String?): Observable<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse?>?
}