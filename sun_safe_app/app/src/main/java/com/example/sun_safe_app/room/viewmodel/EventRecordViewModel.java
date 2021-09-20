package com.example.sun_safe_app.room.viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.EventLog;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.sun_safe_app.room.entity.EventRecord;
import com.example.sun_safe_app.room.repository.EventRecordRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventRecordViewModel extends AndroidViewModel {
    private EventRecordRepository pRepository;
    private LiveData<List<EventRecord>> allPainRecords;
    public EventRecordViewModel(Application application) {
        super(application);
        pRepository = new EventRecordRepository(application);
        allPainRecords = pRepository.getAllCustomers();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<EventRecord> findByIDFuture(int uid){
        return pRepository.findByIDFuture(uid);
    }
    public LiveData<List<EventRecord>> getAllEventRecords() {
        return allPainRecords;
    }
    public void insert(EventRecord painRecord) {
        pRepository.insert(painRecord);
    }

    public void deleteAll() {
        pRepository.deleteAll();
    }
    public void deleteOne(int uid) {
        pRepository.deleteOne(uid);
    }
    public void update(EventRecord painRecord) {
        pRepository.updateCustomer(painRecord);
    }

}