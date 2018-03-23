package com.example.android.weatherapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ForecastActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<cityWeather>> {

    private static final String TAG = "ForecastActivity";
    private static final int FORECAST_LOADER_ID = 2;

    private String mForecastUrl;
    private RecyclerView mRecyclerView;
    private ForecastAdapter mForecastAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mRecyclerView = findViewById(R.id.forecast_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mForecastAdapter = new ForecastAdapter(this, new ArrayList<cityWeather>());
        mRecyclerView.setAdapter(mForecastAdapter);

        mForecastUrl = getIntent().getDataString();
        MainActivity.mLoaderManager.initLoader(FORECAST_LOADER_ID, null, ForecastActivity.this);
    }

    @Override
    public Loader<List<cityWeather>> onCreateLoader(int i, Bundle bundle) {
        return new ForecastLoader(this, mForecastUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<cityWeather>> loader, List<cityWeather> forecastWeather) {

        mForecastAdapter.updateAdapter((ArrayList)forecastWeather);
    }

    @Override
    public void onLoaderReset(Loader<List<cityWeather>> loader) {

    }
}
