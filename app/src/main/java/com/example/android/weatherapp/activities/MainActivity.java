package com.example.android.weatherapp.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.weatherapp.utils.CityWeather;
import com.example.android.weatherapp.R;
import com.example.android.weatherapp.databinding.ActivityMainBinding;
import com.example.android.weatherapp.loaders.WeatherLoader;
import com.example.android.weatherapp.utils.WeatherUtils;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<CityWeather>,
        AdapterView.OnItemSelectedListener{

    private static final String TAG = "MainActivity";

    public static final String WEATHER_REQUEST_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final int WEATHER_LOADER_ID = 1;

    private ActivityMainBinding mMainBainding;

    private int mCityNumber;
    Uri.Builder mUriBuilder;

    private Spinner mCityListSpinner;
    private Button mWeatherButton;
    private Button mForecastButton;

    private ConstraintLayout mExtraWeaherDetails;
    private ConstraintLayout mPrimaryWeatherInfo;
    private LinearLayout mForecastButtonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        mMainBainding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mExtraWeaherDetails = findViewById(R.id.extra_details);
        mPrimaryWeatherInfo = findViewById(R.id.primary_info);
        mForecastButtonLayout = findViewById(R.id.forecast_button_layout);
        mExtraWeaherDetails.setVisibility(View.INVISIBLE);
        mPrimaryWeatherInfo.setVisibility(View.INVISIBLE);
        mForecastButtonLayout.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> cityListAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.city_list));
        mCityListSpinner = findViewById(R.id.city_list_spinner);
        mCityListSpinner.setAdapter(cityListAdapter);
        mCityListSpinner.setOnItemSelectedListener(this);

        if(WeatherLoader.mWeatherData != null){
            bindingUI(WeatherLoader.mWeatherData);
        }

        mWeatherButton = findViewById(R.id.weather_button);
        mWeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e(TAG, "in onClick()");
                getLoaderManager().restartLoader(WEATHER_LOADER_ID, null, MainActivity.this);
            }
        });

        mForecastButton = findViewById(R.id.forecast_button);
        mForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, String.valueOf(WeatherUtils.CityID(mCityNumber)));

                Intent forecastIntent = new Intent(MainActivity.this, ForecastActivity.class);
                forecastIntent.putExtra("cityId", String.valueOf(WeatherUtils.CityID(mCityNumber)));
                startActivity(forecastIntent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "in onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "in onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "in onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "in onStop()");
        super.onStop();
    }

    @Override
    public Loader<CityWeather> onCreateLoader(int i, Bundle bundle) {
        Log.e(TAG, "in onCreateLoader()");
        Uri baseUri = Uri.parse(WEATHER_REQUEST_URL);
        mUriBuilder = baseUri.buildUpon();
        mUriBuilder.appendQueryParameter("id", String.valueOf(WeatherUtils.CityID(mCityNumber)));
        mUriBuilder.appendQueryParameter("units", "metric");
        mUriBuilder.appendQueryParameter("appid", "9332634f1dc13a4d89bb8ad9bbe8fe38");

        return new WeatherLoader(this, mUriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<CityWeather> loader, CityWeather cityWeather) {
        Log.e(TAG, "in onLoadFinished()");
            bindingUI(cityWeather);
    }

    @Override
    public void onLoaderReset(Loader<CityWeather> loader) {
        Log.e(TAG, "WeatherLoader: in onLoaderReset()");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e(TAG, "in onItemSelected()");
        mCityNumber = i;
        Log.e(TAG, "mCityNumber= " + mCityNumber);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bindingUI(CityWeather weatherData){
        if(weatherData != null) {
            //setting primary info
            String name;
            if(mCityNumber == 32){
                name = "Rome";
            }else if(mCityNumber == 43){
                name = "Valetta";
            }else{
                name = weatherData.getCityName();
            }
            mMainBainding.primaryInfo.cityNameTextview.setText(name);
            String temp = String.valueOf(weatherData.getTemperature() + "\u00b0");
            mMainBainding.primaryInfo.temperatureTextview.setText(temp);
            String press = String.valueOf(weatherData.getPressure()) + " hPa";
            mMainBainding.primaryInfo.iconIdImageview.setImageResource(WeatherUtils.getIconForWeatherCondition(weatherData.getIconId()));
            mMainBainding.primaryInfo.weatherDescriptionTextview.setText(weatherData.getWeatherDescription());
            //setting extra details
            mMainBainding.extraDetails.pressureTextview.setText(press);
            String hum = String.valueOf(weatherData.getHumidity()) + "%";
            mMainBainding.extraDetails.humidityTextview.setText(hum);
            String wind = String.valueOf(weatherData.getWindSpeed() + "m/s "
                    + WeatherUtils.windDirection(weatherData.getWindDirection()));
            mMainBainding.extraDetails.windspeedTextview.setText(wind);
            //showing the views
            showUI();
        }else{
            Toast.makeText(this, "unable to fetch data from the network", Toast.LENGTH_LONG).show();
        }
    }

    private void showUI(){
        mExtraWeaherDetails.setVisibility(View.VISIBLE);
        mPrimaryWeatherInfo.setVisibility(View.VISIBLE);
        mForecastButtonLayout.setVisibility(View.VISIBLE);
    }
}
