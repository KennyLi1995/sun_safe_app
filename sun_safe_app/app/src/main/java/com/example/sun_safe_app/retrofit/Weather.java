package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    public String main;
    @SerializedName("id")
    public int id;

}
