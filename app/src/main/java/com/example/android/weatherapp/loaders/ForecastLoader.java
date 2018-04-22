package com.example.android.weatherapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.weatherapp.utils.CityWeather;
import com.example.android.weatherapp.utils.QueryUtils;

import java.util.List;


public class ForecastLoader extends AsyncTaskLoader<List<CityWeather>> {

    private String mUrl;

    public ForecastLoader (Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<CityWeather> loadInBackground() {
        List<CityWeather> forecastWeather = QueryUtils.fetchForecastData(mUrl);
        return forecastWeather;
    }
}
