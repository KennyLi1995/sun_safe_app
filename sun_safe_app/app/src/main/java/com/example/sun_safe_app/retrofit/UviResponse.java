package com.example.sun_safe_app.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UviResponse {
    @SerializedName("result")
    public ArrayList<Weather> weather = new ArrayList<Weather>();

    @SerializedName("name")
    public String name;
}