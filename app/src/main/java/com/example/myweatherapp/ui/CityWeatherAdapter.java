package com.example.myweatherapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.model.City;
import com.example.myweatherapp.model.ForecastWeather;

import java.util.ArrayList;

public class CityWeatherAdapter extends RecyclerView.Adapter<CityWeatherAdapter.CityWeatherViewHolder> {

    private ArrayList<City> mCityList;

    public interface onCityWeatherClickListener {
        void onCityWeatherClicked(ForecastWeather forecastWeather);
    }

    private onCityWeatherClickListener mCityClickListener;

    CityWeatherAdapter(ArrayList<City> cityList,
            onCityWeatherClickListener clickListener) {
        this.mCityList = cityList;
        this.mCityClickListener = clickListener;
    }

    @NonNull
    @Override
    public CityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_weather_list_item, parent, false);
        CityWeatherViewHolder cityWeatherViewHolder = new CityWeatherViewHolder(view);
        view.setOnClickListener(v -> {
            //send forecast weather for clicked city
            ForecastWeather forecastWeather = mCityList.get((Integer) v.getTag())
                    .getForecastWeather();
            mCityClickListener.onCityWeatherClicked(forecastWeather);
        });
        return cityWeatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityWeatherViewHolder holder, int position) {
        ForecastWeather forecastWeather =
                mCityList.get(position).getForecastWeather();
        holder.txtMinTemp.setText("Max: " + (int) forecastWeather.getMinTemp());
        holder.txtMaxTemp.setText("Min: " + (int) forecastWeather.getMaxTemp());
        holder.txtCityName.setText(mCityList.get(position).getTitle());
        holder.txtWeatherState.setText(forecastWeather.getWeatherState());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    class CityWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCityName;
        private TextView txtMaxTemp;
        private TextView txtMinTemp;
        private TextView txtWeatherState;

        CityWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCityName = itemView.findViewById(R.id.txtCityName);
            txtMaxTemp = itemView.findViewById(R.id.txtMaxTemp);
            txtMinTemp = itemView.findViewById(R.id.txtMinTemp);
            txtWeatherState = itemView.findViewById(R.id.txtWeatherState);
        }
    }
}
