package com.example.sun_safe_app.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DateViewModel extends ViewModel {

    private MutableLiveData<String> date;
    private MutableLiveData<String> startTime;
    private MutableLiveData<String> endTime;
    private MutableLiveData<String> locationString;
    private MutableLiveData<String> addressString;


    public DateViewModel(){
        date = new MutableLiveData<>();
        startTime = new MutableLiveData<>();
        endTime = new MutableLiveData<>();
        locationString = new MutableLiveData<>();
        addressString = new MutableLiveData<>();
    }

    public void setMessage(String message) {
        date.setValue(message);
    }
    public LiveData<String> getText() {
        return date;
    }

    public void setStartTime(String message) {
        startTime.setValue(message);
    }
    public LiveData<String> getStartTime() {
        return startTime;
    }

    public void setEndTime(String message) {

        endTime.setValue(message);
    }
    public LiveData<String> getEndTime() {
        return endTime;
    }

    public void setLocationString(String message) {

        locationString.setValue(message);
    }
    public LiveData<String> getLocationString() {

        return locationString;
    }

    public void setAddressString(String message) {
        addressString.setValue(message);
    }
    public LiveData<String> getAddressString() {
        return addressString;
    }

}