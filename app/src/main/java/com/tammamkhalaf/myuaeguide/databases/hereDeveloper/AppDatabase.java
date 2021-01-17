package com.tammamkhalaf.myuaeguide.databases.hereDeveloper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.ItemConverter;
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.PlaceResponse;

@Database(entities = PlaceResponse.class, version = 1)
@TypeConverters(ItemConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract PlacesDao placesDao();

    public static synchronized AppDatabase getInstance(Context context) {

        if(instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class,"AppDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}


