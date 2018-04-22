package com.example.android.weatherapp.utils;

import android.util.Log;

import com.example.android.weatherapp.R;


public class WeatherUtils {

    public static int CityID (int i){
        int cityID = 0;
        switch(i){
            case 0:
                cityID = 6544881;
                break;
            case 1:
                cityID = 3041563;
                break;
            case 2:
                cityID = 323786;
                break;
            case 3:
                cityID = 1526273;
                break;
            case 4:
                cityID = 264371;
                break;
            case 5:
                cityID = 587084;
                break;
            case 6:
                cityID = 792680;
                break;
            case 7:
                cityID = 2950159;
                break;
            case 8:
                cityID = 2661552;
                break;
            case 9:
                cityID = 3060972;
                break;
            case 10:
                cityID = 2800866;
                break;
            case 11:
                cityID = 683506;
                break;
            case 12:
                cityID = 3054643;
                break;
            case 13:
                cityID = 618426;
                break;
            case 14:
                cityID = 2618425;
                break;
            case 15:
                cityID = 2964574;
                break;
            case 16:
                cityID = 658225;
                break;
            case 17:
                cityID = 703448;
                break;
            case 18:
                cityID = 2267057;
                break;
            case 19:
                cityID = 3196359;
                break;
            case 20:
                cityID = 2643741;
                break;
            case 21:
                cityID = 2960316;
                break;
            case 22:
                cityID = 3117735;
                break;
            case 23:
                cityID = 524901;
                break;
            case 24:
                cityID = 2993458;
                break;
            case 25:
                cityID = 5601538;
                break;
            case 26:
                cityID = 146268;
                break;
            case 27:
                cityID = 2988507;
                break;
            case 28:
                cityID = 3193044;
                break;
            case 29:
                cityID = 3067696;
                break;
            case 30:
                cityID = 3413829;
                break;
            case 31:
                cityID = 456172;
                break;
            case 32:
                cityID = 6691831;
                break;
            case 33:
                cityID = 3143244;
                break;
            case 34:
                cityID = 3168070;
                break;
            case 35:
                cityID = 3191281;
                break;
            case 36:
                cityID = 785842;
                break;
            case 37:
                cityID = 727011;
                break;
            case 38:
                cityID = 2673730;
                break;
            case 39:
                cityID = 588409;
                break;
            case 40:
                cityID = 611717;
                break;
            case 41:
                cityID = 3183875;
                break;
            case 42:
                cityID = 3042030;
                break;
            case 43:
                cityID = 2563191;
                break;
            case 44:
                cityID = 6691831;
                break;
            case 45:
                cityID = 2761369;
                break;
            case 46:
                cityID = 593116;
                break;
            case 47:
                cityID = 756135;
                break;
            case 48:
                cityID = 616052;
                break;
            case 49:
                cityID = 6618983;
                break;

            default: cityID = 792680;
        }
        return cityID;
    }

    public static String windDirection (Double degrees){
        String direction = "Unknown";
        if (degrees >= 337.5 || degrees < 22.5) {
            direction = "N";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            direction = "NE";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            direction = "E";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            direction = "SE";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            direction = "S";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            direction = "SW";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            direction = "W";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            direction = "NW";
        }
        return direction;
    }

    public static int getIconForWeatherCondition(int weatherId) {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 500) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 501 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 781) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.ic_clear;
        }

        return R.drawable.ic_storm;
    }
}
