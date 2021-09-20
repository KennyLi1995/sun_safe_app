package com.example.sun_safe_app.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventRecord {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "date")
    @NonNull
    public String date;

    @ColumnInfo(name = "start_time")
    @NonNull
    public String start_time;

    @ColumnInfo(name = "end_time")
    @NonNull
    public String end_time;

    @ColumnInfo(name = "lat")
    @NonNull
    public double lat;

    @ColumnInfo(name = "lon")
    @NonNull
    public double lon;

    @ColumnInfo(name = "address")
    @NonNull
    public String address;

    @ColumnInfo(name = "highest_uvi")
    @NonNull
    public float highest_uvi;

    @ColumnInfo(name = "highest_uvi_hour")
    @NonNull
    public String highest_uvi_hour;

//    @ColumnInfo(name = "temp")
//    @NonNull
//    public int temp;
//
//    @ColumnInfo(name = "weather")
//    @NonNull
//    public String weather;


    @ColumnInfo(name = "advice")
    @NonNull
    public String advice;

    public EventRecord(@NonNull String title, @NonNull String date, @NonNull String start_time, @NonNull String end_time,
                       @NonNull double lat, @NonNull double lon, @NonNull String address,
                       @NonNull float highest_uvi, @NonNull String highest_uvi_hour,
                       @NonNull String advice) {
        this.title = title;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.highest_uvi = highest_uvi;
        this.highest_uvi_hour = highest_uvi_hour;
        this.advice = advice;

    }
}
