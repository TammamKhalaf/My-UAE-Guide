package com.tammamkhalaf.myuaeguide.databases.hereDeveloper

import androidx.room.Database

@Database(entities = PlaceResponse::class, version = 1)
@TypeConverters(ItemConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): com.tammamkhalaf.myuaeguide.databases.hereDeveloper.PlacesDao?

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getInstance(context: android.content.Context?): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance
        }
    }
}