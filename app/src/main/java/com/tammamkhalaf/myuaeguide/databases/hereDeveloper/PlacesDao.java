package com.tammamkhalaf.myuaeguide.databases.hereDeveloper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface PlacesDao {

    @Insert
    Completable insertPlace(PlaceResponse response);

    @Query("select * from placestable")
    Single<List<PlaceResponse>> getPlaces();

    @Delete
    Completable delete(PlaceResponse place);

}
