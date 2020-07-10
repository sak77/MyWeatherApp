package com.example.myweatherapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myweatherapp.R;
import com.example.myweatherapp.model.City;
import com.example.myweatherapp.model.ForecastWeather;
import com.example.myweatherapp.utils.CustomDateUtils;
import com.example.myweatherapp.viewmodel.MyWeatherViewModel;
import com.example.myweatherapp.viewmodel.MyWeatherViewModelFactory;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements
        CityWeatherAdapter.onCityWeatherClickListener {

    /*
        Remove all this boilerplate code!
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    MyWeatherViewModel myWeatherViewModel;
    public static boolean MAIN_EXISTS = false;

    private ArrayList<City> mCityList = new ArrayList<>();
    ProgressBar mProgressBar;

    /*
    This is NOT required! Remove it!
     */
    public MainFragment() {
        // Required empty public constructor
    }

    /*
    Remove unwanted params..remove unwanted code in method body.
     */

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        MyWeatherViewModelFactory myWeatherViewModelFactory =
                new MyWeatherViewModelFactory(getActivity());
        //requireActivity does not return null unlike getActivity.
        myWeatherViewModel = myWeatherViewModelFactory.get(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*
        explore if viewbinding can be used instead....reduce boilerplate code..
         */
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recCityWeather = rootView.findViewById(R.id.recCityWeather);
        recCityWeather.setLayoutManager(new LinearLayoutManager(getActivity()));
        final CityWeatherAdapter cityWeatherAdapter =
                new CityWeatherAdapter(mCityList, this);
        recCityWeather.setAdapter(cityWeatherAdapter);

        //This was not required as we are passing getViewLifecycleOwner()
        //to getLiveCityData. For more details refer detached fragment issue in liveData or
        //refer LiveData notes in evernote
        if (MAIN_EXISTS) {
            //Main Fragment already exists
            return rootView;
        }

        myWeatherViewModel.getLiveCityData().observe(getViewLifecycleOwner(),
                cities -> {
                    mCityList.clear();
                    mCityList.addAll(cities);
                    int visibility = mCityList.size() > 0 ? View.GONE : View.VISIBLE;
                    mProgressBar.setVisibility(visibility);
                    cityWeatherAdapter.notifyDataSetChanged();  //This will force re-draw the entire list...not very efficient
                    //use listadapter with diffutils for more precise way to update data....
                });

        mProgressBar = rootView.findViewById(R.id.progress_circular);
        TextView txtError = rootView.findViewById(R.id.txtError);
        TextView txtForecastDate = rootView.findViewById(R.id.txtDetailForecastDate);

        setForecastDate(txtForecastDate);
        listenForErrors(txtError);
        loadWeatherList();
        return rootView;
    }

    private void loadWeatherList() {
        String[] arrCityNames = getResources().getStringArray(R.array.city_names);
        myWeatherViewModel.getAllCityWeather(arrCityNames);
        //Display progress bar
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void listenForErrors(TextView textView) {
        myWeatherViewModel.getLiveErrorMessage().observe(getViewLifecycleOwner()
                , s -> {
                    //Stop progressbar
                    mProgressBar.setVisibility(View.GONE);
                    //display message
                    textView.setText(s);
                });
    }

    private void setForecastDate(TextView textView) {
        //Get date format to display on screen
        String dateformat = getActivity().getResources().getString(R.string.date_format);
        CustomDateUtils dateUtils = new CustomDateUtils(dateformat);
        //set forecast date
        textView.append(dateUtils.getTomorrowsDate());
    }

    @Override
    public void onCityWeatherClicked(ForecastWeather forecastWeather) {
        //Show details fragment with weather details for selected city
        showWeatherDetails(forecastWeather);
    }

    /*
    This method is not required, it is not being reused anywhere...and its being called from a
    method which itself is just a one liner.

    Also, here i have used fragments with pareleable to pass data between fragments...which is wrong
    use livedata instead to pass data between fragments...
     */
    private void showWeatherDetails(ForecastWeather forecastWeather) {
        WeatherDetailsFragment weatherDetailsFragment = WeatherDetailsFragment
                .newInstance(forecastWeather);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weatherDetailsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}
