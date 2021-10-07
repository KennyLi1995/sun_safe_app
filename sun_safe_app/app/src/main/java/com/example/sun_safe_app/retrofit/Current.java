package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Current {
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
    @SerializedName("uvi")
    public float uvi;
    @SerializedName("temp")
    public float temp;
    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();


}
