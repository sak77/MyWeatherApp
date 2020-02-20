package com.example.myweatherapp.model;

/**
 * Created by sshriwas on 2020-02-16
 * City class which has city related info and current and future date
 * weather info.
 */
public class City {

    private String title;
    private String woeid;
    private CityWeather mCityWeather;   //Current weather
    private ForecastWeather mForecastWeather;   //Forecast weather

    //Maybe use builder here?
    public City(String title, String woeid) {
        this.title = title;
        this.woeid = woeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public CityWeather getCityWeather() {
        return mCityWeather;
    }

    public void setCityWeather(CityWeather cityWeather) {
        mCityWeather = cityWeather;
    }

    public ForecastWeather getForecastWeather() {
        return mForecastWeather;
    }

    public void setForecastWeather(ForecastWeather forecastWeather) {
        mForecastWeather = forecastWeather;
    }
}
