package com.example.android.weatherapp.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.weatherapp.utils.CityWeather;
import com.example.android.weatherapp.loaders.ForecastAdapter;
import com.example.android.weatherapp.R;
import com.example.android.weatherapp.loaders.ForecastLoader;
import com.example.android.weatherapp.utils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CityWeather>> {

    public static final String FORECAST_REQUEST_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String TAG = "ForecastActivity";
    private static final int FORECAST_LOADER_ID = 2;

    private String mForecastUrl;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;
    private LoaderManager forecastLoaderManager;
    Uri.Builder mForecastUriBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = findViewById(R.id.forecast_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mForecastAdapter = new ForecastAdapter(this, new ArrayList<CityWeather>());
        mRecyclerView.setAdapter(mForecastAdapter);

        createForecastUri();

        Log.e(TAG, "Forecast url: " + mForecastUrl);
        forecastLoaderManager = getLoaderManager();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "in onStart()");
        forecastLoaderManager.restartLoader(FORECAST_LOADER_ID, null, ForecastActivity.this);
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "in onBackPressed()");
        super.onBackPressed();
    }

    @Override
    public Loader<List<CityWeather>> onCreateLoader(int i, Bundle bundle) {
        return new ForecastLoader(this, mForecastUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<CityWeather>> loader, List<CityWeather> forecastWeather) {
        Log.e(TAG, "in onLoadFinished()");
        mForecastAdapter.updateAdapter((ArrayList)forecastWeather);
    }

    @Override
    public void onLoaderReset(Loader<List<CityWeather>> loader) {
        Log.e(TAG, "in onLoaderReset()");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createForecastUri(){
        String cityId = getIntent().getStringExtra("cityId");

        Uri baseUri = Uri.parse(FORECAST_REQUEST_URL);
        mForecastUriBuilder = baseUri.buildUpon();
        mForecastUriBuilder.appendQueryParameter("id", cityId);
        mForecastUriBuilder.appendQueryParameter("units", "metric");
        mForecastUriBuilder.appendQueryParameter("appid", "9332634f1dc13a4d89bb8ad9bbe8fe38");

        mForecastUrl = String.valueOf(mForecastUriBuilder);
    }
}
