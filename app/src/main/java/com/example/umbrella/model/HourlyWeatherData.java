package com.example.umbrella.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HourlyWeatherData {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private WeatherMain WeatherMain;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public WeatherMain getWeatherMain() {
        return WeatherMain;
    }

    public void setWeatherMain(WeatherMain WeatherMain) {
        this.WeatherMain = WeatherMain;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

}