package com.tammamkhalaf.myuaeguide.databases.hereDeveloper

import androidx.room.Dao

@Dao
interface PlacesDao {
    @Insert
    fun insertPlace(response: PlaceResponse?): Completable?

    @Query("select * from placestable")
    fun getPlaces(): Single<List<PlaceResponse?>?>?

    @Delete
    fun delete(place: PlaceResponse?): Completable?
}