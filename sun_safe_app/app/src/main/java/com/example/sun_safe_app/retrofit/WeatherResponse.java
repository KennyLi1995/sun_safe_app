package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {
//    @SerializedName("weather")
//    public ArrayList<Weather> weather = new ArrayList<Weather>();
    @SerializedName("current")
    public Current current;

}