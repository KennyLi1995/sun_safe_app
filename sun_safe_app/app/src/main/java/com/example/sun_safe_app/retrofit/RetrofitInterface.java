package com.example.sun_safe_app.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @GET("onecall")
    Call<WeatherResponse> weatherSearch(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("units") String measurement,
            @Query("exclude") String exclude,
            @Query("appid") String keyword);


}
