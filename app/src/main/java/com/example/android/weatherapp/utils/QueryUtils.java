package com.example.android.weatherapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){}

    public static CityWeather fetchWeatherData(String weatherJson){
        CityWeather cityWeather = null;
        URL weatherUrl = createUrl(weatherJson);
        String jsonResponse = makeHttpRequest(weatherUrl);

        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject weatherMainJson = baseJsonResponse.getJSONObject("main");
            JSONObject weatherWindJson = baseJsonResponse.getJSONObject("wind");
            JSONArray weatherArrayJson = baseJsonResponse.getJSONArray("weather");
            JSONObject weatherDescriptionJson = weatherArrayJson.getJSONObject(0);

            cityWeather = new CityWeather(baseJsonResponse.getString("name"),
                    weatherMainJson.getInt("temp"), weatherMainJson.getInt("pressure"),
                    weatherMainJson.getInt("humidity"), weatherWindJson.getDouble("speed"),
                     weatherWindJson.getInt("deg"), weatherDescriptionJson.getInt("id"),
                     weatherDescriptionJson.getString("description"));

        }catch(JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "error fetching JSON results");
        }
        return cityWeather;
    }

    public static List<CityWeather> fetchForecastData(String weatherJson){
        List<CityWeather> forecastCityWeather = new ArrayList<>();
        URL weatherUrl = createUrl(weatherJson);
        String jsonResponse = makeHttpRequest(weatherUrl);

        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray forecastArrayJson = baseJsonResponse.getJSONArray("list");

            for(int i=0; i<forecastArrayJson.length(); i++){
                JSONObject forecastWeather = forecastArrayJson.getJSONObject(i);
                JSONObject weatherMainJson = forecastWeather.getJSONObject("main");
                JSONArray weatherArrayJson = forecastWeather.getJSONArray("weather");
                JSONObject weatherDescriptionJson = weatherArrayJson.getJSONObject(0);

                forecastCityWeather.add(new CityWeather(weatherMainJson.getInt("temp"),
                        weatherDescriptionJson.getInt("id"), weatherDescriptionJson.getString("description"),
                        forecastWeather.getString("dt_txt")));
                Log.e("JSON", "Inserted data");
            }
        }catch(JSONException e){
            e.printStackTrace();
            Log.e("QueryUtils", "error fetching JSON results");
        }
        return forecastCityWeather;
    }

    private static String makeHttpRequest(URL url){
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.e("In makeHttpRequest()", "connecting to network");

            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e("Connection problem - ", "Error response code: " + urlConnection.getResponseCode());
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}
