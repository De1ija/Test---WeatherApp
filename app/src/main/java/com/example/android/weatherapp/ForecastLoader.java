package com.example.android.weatherapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class ForecastLoader extends AsyncTaskLoader<List<cityWeather>> {

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
    public List<cityWeather> loadInBackground() {
        List<cityWeather> forecastWeather = QueryUtils.fetchForecastData(mUrl);
        return forecastWeather;
    }
}
