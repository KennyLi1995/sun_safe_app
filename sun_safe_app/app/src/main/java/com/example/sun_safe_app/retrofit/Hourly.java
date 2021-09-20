package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Hourly {

    @SerializedName("dt")
    public long dt;
    @SerializedName("temp")
    public float temp;

    @SerializedName("uvi")
    public float uvi;

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();

}
