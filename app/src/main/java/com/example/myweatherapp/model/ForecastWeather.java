package com.example.myweatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myweatherapp.network.MetaWeatherClient;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sshriwas on 2020-02-16
 * Class stores info about weather of future date (e.g. tomorrow)
 */
public class ForecastWeather implements Parcelable {
    @SerializedName("min_temp")
    private float minTemp;
    @SerializedName("max_temp")
    private float maxTemp;
    @SerializedName("the_temp")
    private float currTemp;
    @SerializedName("wind_speed")
    private float windSpeed;
    @SerializedName("weather_state_name")
    private String weatherState;
    @SerializedName("weather_state_abbr")
    private String weatherStateAbbr;
    @SerializedName("humidity")
    private String humidity;
    private String cityName;

    private String iconUri;

    protected ForecastWeather(Parcel in) {
        minTemp = in.readFloat();
        maxTemp = in.readFloat();
        currTemp = in.readFloat();
        windSpeed = in.readFloat();
        weatherState = in.readString();
        weatherStateAbbr = in.readString();
        humidity = in.readString();
        iconUri = in.readString();
        cityName = in.readString();
    }

    public static final Creator<ForecastWeather> CREATOR = new Creator<ForecastWeather>() {
        @Override
        public ForecastWeather createFromParcel(Parcel in) {
            return new ForecastWeather(in);
        }

        @Override
        public ForecastWeather[] newArray(int size) {
            return new ForecastWeather[size];
        }
    };

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getCurrTemp() {
        return currTemp;
    }

    public void setCurrTemp(float currTemp) {
        this.currTemp = currTemp;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    public String getIconUri() {
        String icon_url = MetaWeatherClient.BASE_URL + "/static/img/weather/png/64/"
                + this.weatherStateAbbr + ".png";
        return icon_url;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(minTemp);
        parcel.writeFloat(maxTemp);
        parcel.writeFloat(currTemp);
        parcel.writeFloat(windSpeed);
        parcel.writeString(weatherState);
        parcel.writeString(weatherStateAbbr);
        parcel.writeString(humidity);
        parcel.writeString(iconUri);
        parcel.writeString(cityName);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
