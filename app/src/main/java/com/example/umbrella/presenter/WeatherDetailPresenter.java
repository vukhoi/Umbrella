package com.example.umbrella.presenter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbrella.R;
import com.example.umbrella.Util;
import com.example.umbrella.model.HourlyWeatherData;
import com.example.umbrella.model.RetrofitHelper;
import com.example.umbrella.model.WeatherData;
import com.example.umbrella.view.WeatherDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherDetailPresenter {
    private final String TAG = this.getClass().getSimpleName();
    RetrofitHelper retrofitHelper = new RetrofitHelper();
    Context context;

    public WeatherDetailPresenter(Context context){
        this.context = context;
    }

    public void populateView(String zipCode, String unit){

        Observable<WeatherData> observable = retrofitHelper.getWeatherData(zipCode, unit, Util.API_KEY);
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Log.d(TAG, "onNext");
                        List<HourlyWeatherData> dataList = weatherData.getList();

                        String city = weatherData.getCity().getName();
                        String country = weatherData.getCity().getCountry();
                        String temperature = dataList.get(0).getWeatherMain().getTemp().toString();
                        String status = dataList.get(0).getWeather().get(0).getMain();

                        ((WeatherDetailActivity)context).editCurrentWeather(city + ", " + country, temperature, status);

                        List<List<HourlyWeatherData>> dailyWeather = separateListIntoDate(dataList);
                        List<List<LinearLayout>> hourlyTemperature = new ArrayList<>();
                        List<String> dateList = new ArrayList<>();
                        int count = 0;
                        for(List<HourlyWeatherData> day: dailyWeather){
                            hourlyTemperature.add(setUpHourlyWeather(day));
                            if (count == 0) dateList.add("Today");
                            else if (count == 1) dateList.add("Tomorrow");
                            else dateList.add(day.get(0).getDtTxt().split("\\s+")[0]);
                            count += 1;
                        }

                        ((WeatherDetailActivity) context).setUpRecyclerView(hourlyTemperature, dateList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        try {
                            e.printStackTrace();
                        }
                        catch (retrofit2.adapter.rxjava2.HttpException httpException){
                            ((WeatherDetailActivity)context).editCurrentWeather("City not found ", "", "");
                        }

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private List<LinearLayout> setUpHourlyWeather(List<HourlyWeatherData> day) {
        LayoutInflater layoutInflater = ((WeatherDetailActivity)context).getLayoutInflater();
        LinearLayout llWeather = layoutInflater.inflate(R.layout.daily_weather_layout, null).findViewById(R.id.ll_weather);

        List<LinearLayout> weatherRowList = new ArrayList<LinearLayout>();
        String time, iconLink, temperature = null;
        String iconLinkMain = "http://openweathermap.org/img/w/";
        HourlyWeatherData thisDay = null;
        int index;
        LinearLayout currentRow = null;

        for(int i = 0; i < day.size(); i++){
            index = i % 4;
            if(index == 0){
                weatherRowList.add((LinearLayout) layoutInflater.inflate(R.layout.weather_row_layout, null));
                currentRow = weatherRowList.get(weatherRowList.size()-1);
                Log.d("make new row", i + " " + weatherRowList.size());

            }
            thisDay = day.get(i);
            time = thisDay.getDtTxt().split("\\s+")[1];
            iconLink = iconLinkMain + thisDay.getWeather().get(0).getIcon() + ".png";
            temperature = thisDay.getWeatherMain().getTemp().toString();
            fillWeatherEntry(currentRow, index, time.substring(0,time.length()-3), iconLink, temperature);
        }



        int numEntryLastRow = (day.size() % Util.NUM_ENTRY_PER_ROW);
        if (numEntryLastRow == 0){
            return weatherRowList;
        }

        LinearLayout lastRow = weatherRowList.get(weatherRowList.size()-1);
        for(int i = numEntryLastRow; i < Util.NUM_ENTRY_PER_ROW; i++){
            switch(i){
                case 0:
                    lastRow.findViewById(R.id.tv_time0).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.ic0).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.tv_temperature0).setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    lastRow.findViewById(R.id.tv_time1).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.ic1).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.tv_temperature1).setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    lastRow.findViewById(R.id.tv_time2).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.ic2).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.tv_temperature2).setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    lastRow.findViewById(R.id.tv_time3).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.ic3).setVisibility(View.INVISIBLE);
                    lastRow.findViewById(R.id.tv_temperature3).setVisibility(View.INVISIBLE);
                    break;
            }

        }


        return weatherRowList;
    }


    private List<List<HourlyWeatherData>> separateListIntoDate(List<HourlyWeatherData> dataList) {
        List<List<HourlyWeatherData>> dailyWeather = new ArrayList<>();
        String currentDate = "";
        int currentDateIndex = -1;

        for (HourlyWeatherData hourlyData : dataList){
            if(!hourlyData.getDtTxt().split("\\s+")[0].equals(currentDate)){
                currentDate = hourlyData.getDtTxt().split("\\s+")[0];
                currentDateIndex += 1;
                dailyWeather.add(new ArrayList<HourlyWeatherData>());
            }
            dailyWeather.get(currentDateIndex).add(hourlyData);
        }

        return dailyWeather;
    }


    private void fillWeatherEntry(LinearLayout currentRow, int index, String time, String iconLink, String temperature){
        switch(index){
            case 0:
                ((TextView)currentRow.findViewById(R.id.tv_time0)).setText(time);
                Picasso.get().load(iconLink).into((ImageView)currentRow.findViewById(R.id.ic0));
                ((TextView)currentRow.findViewById(R.id.tv_temperature0)).setText(temperature);
                break;
            case 1:
                ((TextView)currentRow.findViewById(R.id.tv_time1)).setText(time);
                Picasso.get().load(iconLink).into((ImageView)currentRow.findViewById(R.id.ic1));
                ((TextView)currentRow.findViewById(R.id.tv_temperature1)).setText(temperature);
                break;
            case 2:
                ((TextView)currentRow.findViewById(R.id.tv_time2)).setText(time);
                Picasso.get().load(iconLink).into((ImageView)currentRow.findViewById(R.id.ic2));
                ((TextView)currentRow.findViewById(R.id.tv_temperature2)).setText(temperature);
                break;
            case 3:
                ((TextView)currentRow.findViewById(R.id.tv_time3)).setText(time);
                Picasso.get().load(iconLink).into((ImageView)currentRow.findViewById(R.id.ic3));
                ((TextView)currentRow.findViewById(R.id.tv_temperature3)).setText(temperature);
                break;
        }
    }
}
