package com.example.sun_safe_app.ui.uvi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class UviFragmentLatLongModel extends ViewModel {

    private MutableLiveData<String> locationString;
    public UviFragmentLatLongModel(){
        locationString = new MutableLiveData<>();
    }
    public void setMessage(String message) {
        locationString.setValue(message);
    }
    public LiveData<String> getText() {
        return locationString;
    }

}