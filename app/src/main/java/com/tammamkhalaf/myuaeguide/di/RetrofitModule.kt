package com.tammamkhalaf.myuaeguide.di

import android.os.Build
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverExplore.hereDevDiscoverExploreApiService
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.hereDevDiscoverHereApiService
import com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.hereDevLockupApiService
import com.tammamkhalaf.myuaeguide.categories.hotels.network.HotelServiceApi
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
    fun provideHereDevDiscoverExploreApiService(): hereDevDiscoverExploreApiService {
        return Retrofit.Builder().baseUrl("https://places.ls.hereapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(hereDevDiscoverExploreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHereDevDiscoverHereApiService(): hereDevDiscoverHereApiService {
        return Retrofit.Builder().baseUrl("https://places.sit.ls.hereapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(hereDevDiscoverHereApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHereDevLockupApiService(): hereDevLockupApiService {
        return Retrofit.Builder().baseUrl("https://places.ls.hereapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient.build())
                .build()
                .create(hereDevLockupApiService::class.java)
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




}