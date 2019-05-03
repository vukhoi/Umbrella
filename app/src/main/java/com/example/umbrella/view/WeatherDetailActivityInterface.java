package com.example.umbrella.view;

import android.widget.LinearLayout;

import java.util.List;

public interface WeatherDetailActivityInterface {
    void editCurrentWeather(String locale, String temperature, String status);
    void setUpRecyclerView(List<List<LinearLayout>> hourlyTemperature, List<String> dateList);
}
