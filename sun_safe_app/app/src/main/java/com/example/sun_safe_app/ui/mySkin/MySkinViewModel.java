package com.example.sun_safe_app.ui.mySkin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MySkinViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MySkinViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}