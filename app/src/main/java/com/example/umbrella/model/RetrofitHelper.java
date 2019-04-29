package com.example.umbrella.model;

import android.util.Log;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private Retrofit retrofit;
    private WeatherUndergroundAPI api;

    public RetrofitHelper() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/")
                .build();
         api = retrofit.create(WeatherUndergroundAPI.class);
    }

    public Observable<WeatherData> getWeatherData(String zip, String unit, String apiKey){
        Log.d("getWeatherData", zip + " " + unit + " " + apiKey);
        return api.getWeatherData(zip, unit, apiKey);
    }
}
