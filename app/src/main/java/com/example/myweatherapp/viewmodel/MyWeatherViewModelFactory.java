package com.example.myweatherapp.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myweatherapp.R;
import com.example.myweatherapp.repository.CityRepository;

/**
 * Created by sshriwas on 2020-02-16
 * MyWeatherViewModelFactory class is a custom Viewmodel factory class which provides instance of
 * MyWeatherViewModel. This class is required because MyWeatherViewModel has dependency on
 * CityRepository class and CityRepository has its own dependency on DateUtil and so on. So
 * to keep things modular and easy to test and to not have any baked-in dependencies in
 * MyWeatherViewModel it is a good practice to create a ViewModel Factory class.
 */
public class MyWeatherViewModelFactory {

    Context mContext;
    //default constructor
    public MyWeatherViewModelFactory(Context context) {
        this.mContext = context;
    }

    public MyWeatherViewModel get(Fragment fragment) {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        return new ViewModelProvider(fragment, viewModelFactory).get(MyWeatherViewModel.class);
    }

    public MyWeatherViewModel get(FragmentActivity fragmentActivity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory();
        return new ViewModelProvider(fragmentActivity, viewModelFactory).get(MyWeatherViewModel.class);
    }

    class ViewModelFactory implements ViewModelProvider.Factory {
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == MyWeatherViewModel.class) {
                String dateformat = mContext.getResources().getString(
                        R.string.date_format);
                CityRepository cityRepository = new CityRepository(dateformat);
                return (T) new MyWeatherViewModel(cityRepository);
            }else {
                throw new RuntimeException(
                        "Unable to create: " + modelClass.toString());
            }
        }
    }
}
