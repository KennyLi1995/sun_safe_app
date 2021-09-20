package com.example.sun_safe_app.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sun_safe_app.room.entity.EventRecord;

import java.util.List;

@Dao
public interface EventRecordDAO {
    @Query("SELECT * FROM EventRecord ORDER BY uid ASC")
    LiveData<List<EventRecord>> getAll();
    @Query("SELECT * FROM EventRecord WHERE uid = :eventRecordId LIMIT 1")
    EventRecord findByID(int eventRecordId);
    @Insert
    void insert(EventRecord aEventRecord);
    @Delete
    void delete(EventRecord aEventRecord);
    @Query("DELETE FROM EventRecord WHERE uid = :uid")
    void deleteOne(int uid);
    @Update
    void updateCustomer(EventRecord aEventRecord);
    @Query("DELETE FROM EventRecord")
    void deleteAll();
}