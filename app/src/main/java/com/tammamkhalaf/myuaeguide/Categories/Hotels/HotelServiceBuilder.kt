package com.tammamkhalaf.myuaeguide.Categories.Hotels

import android.os.Build
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object HotelServiceBuilder {
    const val BASE_URL = "https://hotels4.p.rapidapi.com"

    //create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create Okhttp client
    private val okHttp: Builder = Builder()
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
    private val builder = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
    private val retrofit = builder.build()
    fun <S> buildService(serviceType: Class<S>?): S {
        return retrofit.create(serviceType)
    }
}