package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {
//    @SerializedName("weather")
//    public ArrayList<Weather> weather = new ArrayList<Weather>();
    @SerializedName("current")
    public Current current;

    @SerializedName("timezone_offset")
    public String timezone_offset;

    @SerializedName("hourly")
    public ArrayList<Hourly> hourly = new ArrayList<Hourly>();

    @SerializedName("daily")
    public ArrayList<Daily> daily = new ArrayList<Daily>();

}