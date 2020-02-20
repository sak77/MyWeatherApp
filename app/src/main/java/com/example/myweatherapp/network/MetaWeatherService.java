package com.example.myweatherapp.network;

import com.example.myweatherapp.model.City;
import com.example.myweatherapp.model.CityWeather;
import com.example.myweatherapp.model.ForecastWeather;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sshriwas on 2020-02-14
 * MetaWeatherService uses Retrofit annotations to define interface with 3 methods.
 * Since we have added dependency of retrofit2 to rxjava adapter in our app, it is possible
 * for these methods to return instances of observables instead of the default Call instances.
 * Also when response is a single instance instead of a list of objects it is advisable to use
 * Single instead of Observable (Single vs Observable vs Flowable?).
 */
public interface MetaWeatherService {

    @GET("api/location/search/")
    Observable<List<City>> getCityDetails(@Query("query") String cityName);

    @GET("api/location/{woeid}/{date}/")
    Observable<List<ForecastWeather>> getTomorrowsCityWeather(@Path("woeid") String woeid, @Path("date") String forecastdate);

    @GET("api/location/{woeid}/")
    Single<CityWeather> getCityWeather(@Path("woeid") String woeid);
}
