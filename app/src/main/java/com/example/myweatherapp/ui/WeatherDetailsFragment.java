package com.example.myweatherapp.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myweatherapp.R;
import com.example.myweatherapp.databinding.FragmentWeatherDetailsBinding;
import com.example.myweatherapp.model.ForecastWeather;
import com.example.myweatherapp.utils.DateUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_CITY = "city";

    // TODO: Rename and change types of parameters
    private ForecastWeather mForecastWeather;

    public WeatherDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param forecastWeather Parameter 1.
     * @return A new instance of fragment WeatherDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    static WeatherDetailsFragment newInstance(ForecastWeather forecastWeather) {
        WeatherDetailsFragment fragment = new WeatherDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_CITY, forecastWeather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mForecastWeather = getArguments().getParcelable(KEY_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentWeatherDetailsBinding detailsBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_weather_details, container,
                        false);
        detailsBinding.setWeather(mForecastWeather);
        String dateformat = getActivity().getResources().getString(R.string.date_format);
        detailsBinding.setDateUtils(new DateUtils(dateformat));
        View rootView = detailsBinding.getRoot();
        ProgressBar progressBar = rootView.findViewById(R.id.icon_progress);
        ImageView imgWeatherIcon = rootView.findViewById(R.id.imgWeatherIcon);
        Picasso.get()
                .load(mForecastWeather.getIconUri())
                .error(R.drawable.ic_launcher_background)
                .into(imgWeatherIcon, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        return rootView;
    }
}
