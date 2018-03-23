package com.example.android.weatherapp;



public class cityWeather {

    private String mCityName;
    private int mTemperature;
    private int mPressure;
    private int mHumidity;
    private double mWindSpeed;

    private double mWindDirection;
    private int mIconId;
    private String mWeatherDescription;
    private String mDate;


    public cityWeather(String cityName, int temperature, int pressure, int humidity, double windSpeed,
                       double windDirection, int iconId, String weatherDescription){
        mCityName = cityName;
        mTemperature = temperature;
        mPressure = pressure;
        mHumidity = humidity;
        mWindSpeed = windSpeed;
        mWindDirection = windDirection;
        mIconId = iconId;
        mWeatherDescription = weatherDescription;
    }

    public cityWeather(int temperature, int iconId, String weatherDescription, String date){
        mTemperature = temperature;
        mIconId = iconId;
        mWeatherDescription = weatherDescription;
        mDate = date;
    }

    public String getCityName(){
        return mCityName;
    }
    public int getTemperature(){
        return mTemperature;
    }
    public int getPressure(){
        return mPressure;
    }
    public int getHumidity(){
        return mHumidity;
    }
    public double getWindSpeed(){
        return mWindSpeed;
    }
    public double getWindDirection(){
        return mWindDirection;
    }
    public int getIconId(){
        return mIconId;
    }
    public String getWeatherDescription(){
        return mWeatherDescription;
    }
    public String getDate(){
        return mDate;
    }

}
