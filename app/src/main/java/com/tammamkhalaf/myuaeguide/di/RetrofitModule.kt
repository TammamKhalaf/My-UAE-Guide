package com.tammamkhalaf.myuaeguide.di

import android.os.Build
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
import com.tammamkhalaf.myuaeguide.categories.nearbyPlaces.network.TrueWayPlacesServiceApi
import com.tammamkhalaf.myuaeguide.categories.openTripMap.OpenTripMapServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    //create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    //create Okhttp client
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                var request = chain.request()
                request = request.newBuilder()
                        .addHeader("x-device-type", Build.DEVICE)
                        .addHeader("Accept-Language", Locale.getDefault().language)
                        .build()
                chain.proceed(request)
            })
            .addInterceptor(logger)


    @Provides
    @Singleton
    fun provideTWPApiService(): TrueWayPlacesServiceApi {
        return Retrofit.Builder().baseUrl("https://trueway-places.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(TrueWayPlacesServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHotelApiService(): HotelServiceApi {
        return Retrofit.Builder().baseUrl("https://hotels4.p.rapidapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(HotelServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenTripMapApiService(): OpenTripMapServiceApi {
        return Retrofit.Builder().baseUrl("https://api.opentripmap.com/0.1/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(OpenTripMapServiceApi::class.java)
    }
}