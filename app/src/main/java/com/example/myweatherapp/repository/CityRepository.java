package com.example.myweatherapp.repository;

import com.example.myweatherapp.model.City;
import com.example.myweatherapp.network.MetaWeatherClient;
import com.example.myweatherapp.network.MetaWeatherService;
import com.example.myweatherapp.utils.CustomDateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sshriwas on 2020-02-16
 * CityRepository is responsible for data operations. Repository layer is usually responsible for
 * providing data to the viewmodel layer. This data can come via network api or local storage such
 * as Room DB.
 *
 * In our case, CityRepository holds instance of MetWeatherService interface. MetaWeatherService is
 * an interface which exposes the Network APIs to this app via Retrofit's annotations.
 *
 * CityRepository has a single public method which provides a list of observables to the viewmodel.
 * These observables hold info about individual network requests which need to be made to get
 * City weather info for the different 5 cities. MyWeatherViewModel then uses Rxjava zip operator
 * to combine the requests, send them to the Network API and handle the network API response in a
 * graceful manner.
 *
 * getCityObservables loops through the names of the cities and does 2 things -
 * 1. create first request which gets city info for each city name
 * 2. Then uses the flatMap operator which combines the response from step 1 with the 2nd
 * API request to get weather info for each city object. This is used to udpate Weather info into
 * into city object using another map operator. flatMap is similar to MediatorLiveData (or transformations?).
 * It can combine observables from different sources.(map vs flatmap??)
 *
 */
public class CityRepository {

    private String mDateFormat;

    public CityRepository(String dateFormat) {
        this.mDateFormat = dateFormat;
    }

    private MetaWeatherService metaWeatherService =
            MetaWeatherClient.getRetrofit().create(MetaWeatherService.class);

    public List<Observable<City>> getCityObservables(String[] arrCityNames) {
        List<Observable<City>> lstObservables = new ArrayList<>();

        for (String arrCityName : arrCityNames) {
            //response from getCityInfoObservable is Observable<City>
            //we then use flatMap operator to combine this response with response of
            //getWeatherObservable to provide a final response of type Observable<City>
            //Is this mediatorlivedata or transformations.map??
            Observable<City> cityObservable = getCityInfoObservable(arrCityName)
                    .flatMap(this::getWeatherObservable).subscribeOn(Schedulers.io());
            lstObservables.add(cityObservable);
        }
        return lstObservables;
    }


    private Observable<City> getCityInfoObservable(String cityName) {
        return metaWeatherService.getCityDetails(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //the response of getCityDetails() is Observable<list<City>>
                //Hence we use flatmap here so that we can convert it into a single observable<City>
                //using fromIterable() method.
                // This single observable can then be used to emit the individual cities.
                .flatMap(
                        (Function<List<City>, ObservableSource<City>>) cities ->
                                Observable.fromIterable(cities).subscribeOn(Schedulers.io()));
    }

    private Observable<City> getWeatherObservable(City city) {
        //Get forecast date
        CustomDateUtils dateUtils = new CustomDateUtils(mDateFormat);
        String forecastDate = dateUtils.getTomorrowsDate();
        return metaWeatherService.getTomorrowsCityWeather(city.getWoeid(), forecastDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(forecastWeathers -> {
                    city.setForecastWeather(forecastWeathers.get(0));
                    city.getForecastWeather().setCityName(city.getTitle());
                    return city;
                });
    }

}
