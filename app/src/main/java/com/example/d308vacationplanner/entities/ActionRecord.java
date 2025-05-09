package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "records")
public class ActionRecord {

    @PrimaryKey(autoGenerate = true)
    private int ID;
    private String description;
    private String tripTitle;
    private String dateRecorded;

    public ActionRecord(String description, String tripTitle, String dateRecorded) {
        this.description = description;
        this.tripTitle = tripTitle;
        this.dateRecorded = dateRecorded;
    }

    public int getID() { return ID; }

    public void setID(int recordID) { this.ID = recordID; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTripTitle() { return tripTitle; }

    public void setTripTitle(String tripTitle) { this.tripTitle = tripTitle; }

    public String getDateRecorded() { return dateRecorded; }

    public void setDateRecorded(String dateRecorded) { this.dateRecorded = dateRecorded; }
}