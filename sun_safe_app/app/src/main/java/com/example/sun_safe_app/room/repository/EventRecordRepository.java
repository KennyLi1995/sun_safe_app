package com.example.sun_safe_app.room.repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.sun_safe_app.room.dao.EventRecordDAO;
import com.example.sun_safe_app.room.database.EventRecordDatabase;
import com.example.sun_safe_app.room.entity.EventRecord;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class EventRecordRepository {
    private EventRecordDAO eventRecordDao;
    private LiveData<List<EventRecord>> allEventRecords;
    public EventRecordRepository(Application application){
        EventRecordDatabase db = EventRecordDatabase.getInstance(application);
        eventRecordDao =db.eventDataDao();
        allEventRecords= eventRecordDao.getAll();
    }
    // Room executes this query on a separate thread
    public LiveData<List<EventRecord>> getAllCustomers() {
        return allEventRecords;
    }
    public  void insert(final EventRecord customer){
        EventRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventRecordDao.insert(customer);
            }
        });
    }
    public void deleteAll(){
        EventRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventRecordDao.deleteAll();
            }
        });
    }
    public void delete(final EventRecord customer){
        EventRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventRecordDao.delete(customer);
            }
        });
    }

    public void deleteOne(final int uid){
        EventRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventRecordDao.deleteOne(uid);
            }
        });
    }
    public void updateCustomer(final EventRecord customer){
        EventRecordDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                eventRecordDao.updateCustomer(customer);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<EventRecord> findByIDFuture(final int customerId) {
        return CompletableFuture.supplyAsync(new Supplier<EventRecord>() {
            @Override
            public EventRecord get() {  return eventRecordDao.findByID(customerId);
            }
        }, EventRecordDatabase.databaseWriteExecutor);
    }
}