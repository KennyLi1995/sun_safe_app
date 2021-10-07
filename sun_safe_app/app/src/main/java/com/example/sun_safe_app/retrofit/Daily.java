package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Daily {

    @SerializedName("dt")
    public long dt;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
    @SerializedName("uvi")
    public float uvi;

}
