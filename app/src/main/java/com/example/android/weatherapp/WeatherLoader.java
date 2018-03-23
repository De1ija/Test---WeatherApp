package com.example.android.weatherapp;

import android.content.AsyncTaskLoader;
import android.content.Context;


public class WeatherLoader extends AsyncTaskLoader<cityWeather> {

    private String mUrl;
/*    public static cityWeather mWeatherData;*/

    public WeatherLoader (Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public cityWeather loadInBackground() {
        cityWeather belgradeWeather = QueryUtils.fetchWeatherData(mUrl);
        return belgradeWeather;
    }

/*    @Override
    public void deliverResult(cityWeather data) {
        mWeatherData = data;
        super.deliverResult(data);
    }*/
}
