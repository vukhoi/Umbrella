package com.example.umbrella.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherUndergroundAPI {
    @GET("data/2.5/forecast")
    Observable<WeatherData> getWeatherData(@Query("zip") String zip,
                                           @Query("units") String unit,
                                           @Query("appid") String apiKey);


}

