package com.example.sun_safe_app.ui.activityPlan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityWeatherViewModel extends ViewModel {

    private MutableLiveData<Boolean> mText;
    public ActivityWeatherViewModel(){
        mText = new MutableLiveData<>();
    }


    public void setMessage(Boolean message) {
        mText.setValue(message);
    }
    public LiveData<Boolean> getText() {
        return mText;
    }

}