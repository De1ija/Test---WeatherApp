package com.example.android.weatherapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.weatherapp.utils.CityWeather;
import com.example.android.weatherapp.utils.QueryUtils;


public class WeatherLoader extends AsyncTaskLoader<CityWeather> {

    private static final String TAG = "WeatherLoader";
    private String mUrl;
    public static CityWeather mWeatherData;

    public WeatherLoader (Context context, String url){
        super(context);
        mUrl = url;
        Log.e(TAG, "in WeatherLoader()");
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.e(TAG, "in onStartLoading()");
    }

    @Override
    public CityWeather loadInBackground() {
        Log.e(TAG, "in loadInBackground()");
        CityWeather belgradeWeather = QueryUtils.fetchWeatherData(mUrl);
        Log.e(TAG, "in loadInBackground(), after fetchting data");
        return belgradeWeather;
    }

    @Override
    protected void onReset() {
        Log.e(TAG, "in onReset()");
        super.onReset();
    }

    @Override
    public void deliverResult(CityWeather data) {
        mWeatherData = data;
        super.deliverResult(data);
        Log.e(TAG, "in deliverResult()");
    }
}
