package com.example.sun_safe_app.utils;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Bitmap> bitmapMutableLiveData;

    public LiveData<Bitmap> getBitmapMutableLiveData() {
        if (bitmapMutableLiveData == null) {
            bitmapMutableLiveData = new MutableLiveData<Bitmap>();
        }
        return bitmapMutableLiveData;
    }

    public void setBitmap(Bitmap bitmapImage) {
        bitmapMutableLiveData.postValue(bitmapImage);
    }
}

