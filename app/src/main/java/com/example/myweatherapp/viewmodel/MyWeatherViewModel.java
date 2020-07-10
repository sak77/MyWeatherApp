package com.example.myweatherapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myweatherapp.repository.CityRepository;
import com.example.myweatherapp.model.City;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * MyWeatherViewModel class is the ViewModel which drives the UI layer of this app.
 * It contains a liveData of list of cities to display weather info for. And it also
 * contains a liveData to update UI in case of error while performing network operations.
 * It contains a public method getAllCityWeather() which is invoked from the MainFragment.
 * This method is used to get all city and city weather info and update the livedata. (why use
 * postValue instead of setValue ?).
 *
 * MyWeatherViewModel does not do the actual work of making the API requests. That is done by
 * CityRepository class. MyWeatherViewModel only invokes the correct methods of CityRepository class
 * and then handles the response from the CityRepository instance.
 */
public class MyWeatherViewModel extends ViewModel {

    private MutableLiveData<List<City>> mLstLiveCityData
            = new MutableLiveData<>();

    private MutableLiveData<String> mLiveErrorString = new MutableLiveData<>();

    private static final String TAG = "MyWeatherViewModel";
    private final CityRepository mCityRepository;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    MyWeatherViewModel(CityRepository cityRepository) {
        this.mCityRepository = cityRepository;
    }

    public void getAllCityWeather(String[] arrCityNames) {
        List<Observable<City>> lstObservables = mCityRepository.getCityObservables(arrCityNames);

        /*
        lstObservables is a list of Observable<City>, so the input parameter to the function should be
        the same and not Object[]. Also the return type is incorrect as it should be maybe City instance.

        And the zip operator is not required here, since the function is not really doing anything...
         */
        Observable.zip(lstObservables, (Function<Object[], Object>) objects -> objects)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(Object o) {
                        updateCityLiveData((Object[]) o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLiveErrorString.postValue(e.getMessage());
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void updateCityLiveData(Object[] objects) {
        List<City> lstCity = new ArrayList<>();
        //There is a converter library that converts observable to livedata. Could that have
        //been used here to get livedata from city list?
        for (Object currCityObj : objects) {
            City currCity = (City) currCityObj;
            lstCity.add(currCity);
        }
        mLstLiveCityData.postValue(lstCity);
    }

    public LiveData<List<City>> getLiveCityData() {
        return mLstLiveCityData;
    }

    public LiveData<String> getLiveErrorMessage() {
        return mLiveErrorString;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }
}
