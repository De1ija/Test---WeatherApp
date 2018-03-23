package com.example.android.weatherapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.weatherapp.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<cityWeather>,
        AdapterView.OnItemSelectedListener{

    private static final String TAG = "MainActivity";
/*    public static final String WEATHER_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/weather?id=792680&appid=9332634f1dc13a4d89bb8ad9bbe8fe38&units=metric";*/
    public static final String WEATHER_REQUEST_URL = "http://api.openweathermap.org/data/2.5/weather";
    public static final String FORECAST_REQUEST_URL = "http://api.openweathermap.org/data/2.5/forecast";

    private static final int WEATHER_LOADER_ID = 1;
    public static LoaderManager mLoaderManager;

    private ActivityMainBinding mMainBainding;

    private int mCityNumber = 0;
    Uri.Builder mUriBuilder;
    Uri.Builder mForecastUriBuilder;

    private Spinner mCityListSpinner;
    private Button mWeatherButton;
    private Button mForecastButton;

    private ConstraintLayout mExtraWeaherDetails;
    private ConstraintLayout mPrimaryWeatherInfo;
    private LinearLayout mForecastButtonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        mWeatherButton = findViewById(R.id.weather_button);
        mWeatherButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mLoaderManager = getLoaderManager();
                mLoaderManager.restartLoader(WEATHER_LOADER_ID, null, MainActivity.this);
                mLoaderManager.initLoader(WEATHER_LOADER_ID, null, MainActivity.this);
            }
        });

        mForecastButton = findViewById(R.id.forecast_button);
        mForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri baseUri = Uri.parse(FORECAST_REQUEST_URL);
                mForecastUriBuilder = baseUri.buildUpon();
                mForecastUriBuilder.appendQueryParameter("id", String.valueOf(WeatherUtils.CityID(mCityNumber)));
                mForecastUriBuilder.appendQueryParameter("units", "metric");
                mForecastUriBuilder.appendQueryParameter("appid", "9332634f1dc13a4d89bb8ad9bbe8fe38");
                Uri forecastUri = Uri.parse(String.valueOf(mForecastUriBuilder));

                Intent forecastIntent = new Intent(MainActivity.this, ForecastActivity.class);
                forecastIntent.setData(forecastUri);
                startActivity(forecastIntent);

            }
        });

    }

    @Override
    public Loader<cityWeather> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(WEATHER_REQUEST_URL);
        mUriBuilder = baseUri.buildUpon();
        mUriBuilder.appendQueryParameter("id", String.valueOf(WeatherUtils.CityID(mCityNumber)));
        mUriBuilder.appendQueryParameter("units", "metric");
        mUriBuilder.appendQueryParameter("appid", "9332634f1dc13a4d89bb8ad9bbe8fe38");

        return new WeatherLoader(this, mUriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<cityWeather> loader, cityWeather cityWeather) {
            bindingUI(cityWeather);
    }

    @Override
    public void onLoaderReset(Loader<cityWeather> loader) {
        mLoaderManager.restartLoader(WEATHER_LOADER_ID, null, MainActivity.this);
        Log.e(TAG, "WeatherLoader: in onLoaderReset()");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mCityNumber = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void bindingUI(cityWeather weatherData){
        if(weatherData != null) {
            //setting primary info
            String name = weatherData.getCityName();
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
            mExtraWeaherDetails.setVisibility(View.VISIBLE);
            mPrimaryWeatherInfo.setVisibility(View.VISIBLE);
            mForecastButtonLayout.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "unable to fetch data from the network", Toast.LENGTH_LONG).show();
        }
    }
}
