package com.example.d308vacationplanner.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.d308vacationplanner.entities.ActionRecord;

import java.util.List;

@Dao
public interface ActionRecordDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecord(ActionRecord record);

    @Query("SELECT * FROM records ORDER BY ID ASC")
    LiveData<List<ActionRecord>> getAllRecords();

    @Query("SELECT * FROM records WHERE tripTitle=:tripTitle ORDER BY ID ASC")
    LiveData<List<ActionRecord>> searchRecords(String tripTitle);
}
