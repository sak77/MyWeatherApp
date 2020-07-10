package com.example.myweatherapp.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sshriwas on 2020-02-14
 * MetaWeather client has a single method which provides instance of
 * retrofit class. addCallAdapterFactory allows rertrofit to return rxjava2 observables instead
 * of default Call instance.
 */
public class MetaWeatherClient_old {
    public static String BASE_URL = "https://www.metaweather.com/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //.addConverterFactory(createGsonConverter())
                    .build();
        }
        return retrofit;
    }

}
