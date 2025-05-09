package com.example.d308vacationplanner.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;


import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.ActionRecord;
import com.example.d308vacationplanner.entities.Vacation;
import com.example.d308vacationplanner.dao.ActionRecordDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.dao.ExcursionDAO;


@Database(entities = {Vacation.class, Excursion.class, ActionRecord.class}, version = 8, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {


    private static volatile VacationDatabase INSTANCE;
    //Using abstraction here
    public abstract ActionRecordDAO actionRecordDAO();
    public abstract ExcursionDAO excursionDAO();
    public abstract VacationDAO vacationDAO();


    public static VacationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    VacationDatabase.class, "vacation_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
