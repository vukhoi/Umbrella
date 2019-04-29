package com.example.umbrella.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.umbrella.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private final Context context;
    private List<List<LinearLayout>> weatherRowList;
    private List<String> dateList;

    public CustomAdapter(Context context, List<List<LinearLayout>> weatherRowList, List<String> dateList){
        this.context = context;
        this.weatherRowList = weatherRowList;
        this.dateList = dateList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardView cardView = (CardView) layoutInflater.inflate(R.layout.daily_weather_layout, null);
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
            holder.llWeather.addView(row);
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size()  ;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llWeather;
        TextView tvDate;

        public CustomViewHolder(View itemView) {
            super(itemView);
            llWeather = itemView.findViewById(R.id.ll_weather);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
