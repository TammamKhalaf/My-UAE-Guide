package com.tammamkhalaf.myuaeguide.Categories.Hotels;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    public static final String BASE_URL = "https://trueway-places.p.rapidapi.com/";


    //create logger
    private static HttpLoggingInterceptor logger = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    //create Okhttp client
    private static OkHttpClient.Builder okHttp = new OkHttpClient.Builder().addInterceptor(logger);

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build());

    private static Retrofit retrofit = builder.build();


    public static <S> S buildService(Class<S> serviceType){
        return retrofit.create(serviceType);
    }

}
