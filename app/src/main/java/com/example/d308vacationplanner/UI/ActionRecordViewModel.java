package com.example.d308vacationplanner.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.ActionRecord;

import java.util.List;

public class ActionRecordViewModel extends AndroidViewModel {
    private final Repository repository;
    private LiveData<List<ActionRecord>> allRecords;

    public ActionRecordViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allRecords = repository.getAllRecords(); // Assuming repository method is not renamed
    }

    public LiveData<List<ActionRecord>> getAllRecords() {
        return allRecords;
    }

    public void searchRecords(String tripTitle) {
        allRecords = repository.searchRecords(tripTitle); // Assuming repository method is not renamed
    }
}