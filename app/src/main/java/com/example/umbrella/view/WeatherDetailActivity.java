package com.example.umbrella.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbrella.R;
import com.example.umbrella.Util;
import com.example.umbrella.presenter.WeatherDetailPresenter;
import com.example.umbrella.presenter.WeatherDetailPresenterInterface;


import java.util.List;

public class WeatherDetailActivity extends AppCompatActivity implements WeatherDetailActivityInterface{
    private final String TAG = this.getClass().getSimpleName();
    WeatherDetailPresenterInterface presenter;
    TextView tvLocale, tvTemperature, tvStatus;
    ImageView ivSettiing;
    RecyclerView recyclerView;
    ConstraintLayout clCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        Intent intent = getIntent();

        setUpViews();

        presenter = new WeatherDetailPresenter(this);

        presenter.populateView(intent.getStringExtra(Util.ZIP),
                intent.getStringExtra(Util.UNIT));
    }



    private void setUpViews() {
        tvLocale = findViewById(R.id.tv_locale);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvStatus = findViewById(R.id.tv_status);
        ivSettiing = findViewById(R.id.iv_setting);
        recyclerView = findViewById(R.id.recyclerView);
        clCurrentWeather = findViewById(R.id.cl_current_weather);

        ivSettiing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void editCurrentWeather(String locale, String temperature, String status){
        Log.d(TAG, "editCurrentWeather");
        boolean isWarm = false;
        double temperatureDouble = Double.parseDouble(temperature.substring(0, temperature.length()-1));
        tvLocale.setText(locale);
        tvTemperature.setText(temperature);
        tvStatus.setText(status);

        if(Util.INPUT_UNIT.equals(getResources().getString(R.string.fahrenheit))){
            if(temperatureDouble > Util.TEMP_WARM_START_F){
                isWarm = true;
            }
        }
        else if(Util.INPUT_UNIT.equals(getResources().getString(R.string.celsius))){
            if(temperatureDouble > Util.TEMP_WARM_START_C){
                isWarm = true;
            }
        }

        if (isWarm) clCurrentWeather.setBackgroundColor(getResources().getColor(R.color.colorWarm));
        else clCurrentWeather.setBackgroundColor(getResources().getColor(R.color.colorCold));

    }

    public void setUpRecyclerView(List<List<LinearLayout>> hourlyTemperature, List<String> dateList){
        Log.d("recyclerview", dateList.toString());
        CustomAdapter customAdapter = new CustomAdapter(hourlyTemperature, dateList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
