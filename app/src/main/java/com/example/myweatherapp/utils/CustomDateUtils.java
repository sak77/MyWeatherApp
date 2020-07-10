package com.example.myweatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sshriwas on 2020-02-16
 * Simple class to hold date util methods
 */
public class CustomDateUtils {

    String mExpectedFormat;
    public CustomDateUtils(String expectedFormat) {
        mExpectedFormat = expectedFormat;
    }

    public String getTomorrowsDate() {
        //Get current Date
        Calendar calendar = Calendar.getInstance();
        //add one day
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mExpectedFormat);
        String tomorrow_date = simpleDateFormat.format(tomorrow);
        return tomorrow_date;
    }
}
