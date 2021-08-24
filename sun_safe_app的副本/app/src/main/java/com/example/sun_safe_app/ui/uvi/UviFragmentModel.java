package com.example.sun_safe_app.ui.uvi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UviFragmentModel extends ViewModel {

    private MutableLiveData<String> mText;
    public UviFragmentModel(){
        mText = new MutableLiveData<>();
    }
    public void setMessage(String message) {
        mText.setValue(message);
    }
    public LiveData<String> getText() {
        return mText;
    }

}