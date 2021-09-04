package com.example.sun_safe_app.ui.sunscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SunscreenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SunscreenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}