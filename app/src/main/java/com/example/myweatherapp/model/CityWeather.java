package com.example.myweatherapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sshriwas on 2020-02-16
 * Class stores current weather info for a particular city.
 * It implements Parcelable since it needs to be sent from one fragment to another via
 * Bundle.put
 */
public class CityWeather implements Parcelable {
    @SerializedName("title")
    private String cityName;
    @SerializedName("woeid")
    private String woeid;
    @SerializedName("sun_rise")
    private String sunRise;
    @SerializedName("sun_set")
    private String sunSet;

    @SerializedName("time")
    private String date;

    @SerializedName("consolidated_weather")
    private List<ConsolidatedWeather> mConsolidatedWeatherList;

    //default constructor
    CityWeather() {
    }

    protected CityWeather(Parcel in) {
        cityName = in.readString();
        woeid = in.readString();
        sunRise = in.readString();
        sunSet = in.readString();
        date = in.readString();
    }

    public static final Creator<CityWeather> CREATOR = new Creator<CityWeather>() {
        @Override
        public CityWeather createFromParcel(Parcel in) {
            return new CityWeather(in);
        }

        @Override
        public CityWeather[] newArray(int size) {
            return new CityWeather[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWoeid() {
        return woeid;
    }

    void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSunSet() { return sunSet; }

    public void setSunSet(String sunSet) {
        this.sunSet = sunSet;
    }

    public String getSunRise() {
        return sunRise;
    }

    public void setSunRise(String sunRise) {
        this.sunRise = sunRise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(cityName);
        parcel.writeString(woeid);
        parcel.writeString(sunRise);
        parcel.writeString(sunSet);
        parcel.writeString(date);
    }

    public List<ConsolidatedWeather> getConsolidatedWeatherList() {
        return mConsolidatedWeatherList;
    }

    public void setConsolidatedWeatherList(
            List<ConsolidatedWeather> consolidatedWeatherList) {
        mConsolidatedWeatherList = consolidatedWeatherList;
    }


    public static class ConsolidatedWeather {
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
        private String iconUri;

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

        void setWeatherState(String weatherState) {
            this.weatherState = weatherState;
        }

        public String getWeatherStateAbbr() {
            return weatherStateAbbr;
        }

        public void setWeatherStateAbbr(String weatherStateAbbr) {
            this.weatherStateAbbr = weatherStateAbbr;
        }

/*        public String getIconUri() {
            String icon_url = MetaWeatherClient.BASE_URL + "/static/img/weather/png/64/"
                    + this.weatherStateAbbr + ".png";
            return icon_url;
        }*/
    }
}
