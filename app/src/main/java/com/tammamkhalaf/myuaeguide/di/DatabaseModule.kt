package com.tammamkhalaf.myuaeguide.di

import android.content.Context
import androidx.room.Room
import com.tammamkhalaf.myuaeguide.databases.hereDeveloper.PlacesDao
import com.tammamkhalaf.myuaeguide.databases.hereDeveloper.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): PlacesDao {
        return appDatabase.placesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
                appContext,
                AppDatabase::class.java,
                "AppDatabase"
        ).build()
    }
}