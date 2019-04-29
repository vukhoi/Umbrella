package com.example.umbrella.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbrella.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<List<LinearLayout>> weatherRowList;
    private List<String> dateList;

    CustomAdapter(List<List<LinearLayout>> weatherRowList, List<String> dateList){
        this.weatherRowList = weatherRowList;
        this.dateList = dateList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LinearLayout cardView = (LinearLayout) layoutInflater.inflate(R.layout.daily_weather_layout, null);
        if (cardView.getParent() != null) {
            ((ViewGroup) cardView.getParent()).removeView(cardView);
        }
        return new CustomViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        List<LinearLayout> currentDate = weatherRowList.get(position);
        String date = dateList.get(position);
        holder.tvDate.setText(date);
        for(LinearLayout row: currentDate){
            if (row.getParent() != null) {
                ((ViewGroup) row.getParent()).removeView(row);
            }
            holder.llWeather.addView(row);
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size()  ;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llWeather;
        TextView tvDate;

        CustomViewHolder(View itemView) {
            super(itemView);
            llWeather = itemView.findViewById(R.id.ll_weather);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
