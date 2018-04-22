package com.example.android.weatherapp.loaders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.weatherapp.R;
import com.example.android.weatherapp.utils.CityWeather;
import com.example.android.weatherapp.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;


public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {

    private static final String TAG = "ForecastAdapter";
    private final Context mContext;
    private  List<CityWeather> mForecast;

    public ForecastAdapter (Context context, List<CityWeather> forecast){
        mContext = context;
        mForecast = forecast;
    }
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.forecast_list_item, parent, false);
        view.setFocusable(true);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {

        CityWeather forecast = mForecast.get(position);

        holder.iconView.setImageResource(WeatherUtils.getIconForWeatherCondition(forecast.getIconId()));
        holder.dateView.setText(forecast.getDate());
        holder.descriptionView.setText(forecast.getWeatherDescription());
        String temp = String.valueOf(forecast.getTemperature()+"\u00b0");
        holder.temperatureView.setText(temp);
    }

    public void updateAdapter(ArrayList<CityWeather> forecast){
        Log.e(TAG, "in updateAdapter()");
        mForecast = forecast;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mForecast.size();
    }

    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder{

        final ImageView iconView;
        final TextView dateView;
        final TextView descriptionView;
        final TextView temperatureView;


        public ForecastAdapterViewHolder(View view){
            super(view);
            iconView = (ImageView) view.findViewById(R.id.weather_icon);
            dateView = (TextView) view.findViewById(R.id.date);
            descriptionView = (TextView) view.findViewById(R.id.weather_description);
            temperatureView = (TextView) view.findViewById(R.id.temperature);

        }
    }
}
