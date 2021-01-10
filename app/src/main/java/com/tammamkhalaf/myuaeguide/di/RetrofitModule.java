package com.tammamkhalaf.myuaeguide.di;

import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi;
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitModule {

    @Provides
    @Singleton
    public static TrueWayPlacesServiceApi provideTWPApiService(){
        return new Retrofit.Builder().baseUrl("https://trueway-places.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(TrueWayPlacesServiceApi.class);
    }

    @Provides
    @Singleton
    public static HotelServiceApi provideHotelApiService(){
        return new Retrofit.Builder().baseUrl("https://hotels4.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(HotelServiceApi.class);
    }

}
