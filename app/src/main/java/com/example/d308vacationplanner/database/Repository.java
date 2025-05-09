package com.example.d308vacationplanner.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.d308vacationplanner.dao.ActionRecordDAO;
import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.entities.ActionRecord;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.example.d308vacationplanner.utils.DateUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {



    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;

    private final ActionRecordDAO mActionRecordDAO;




    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;



    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //added databases for testing, if it works removed this comment
    public Repository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
        mActionRecordDAO = db.actionRecordDAO();


    }



    public List<Vacation> getmRelativeVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getRelativeVacations();
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllVacations;
    }

    public void insert (Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);

            ActionRecord record = new ActionRecord(
                    "Vacation Created",
                    vacation.getVacationName(),
                    DateUtils.getCurrentTimestamp()
            );
            mActionRecordDAO.insertRecord(record);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion> getmAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }

    //Logging Feature
    public void insert(ActionRecord record) {
        databaseExecutor.execute(() -> mActionRecordDAO.insertRecord(record));
    }


    public LiveData<List<ActionRecord>> getAllRecords() {
        return mActionRecordDAO.getAllRecords();
    }

    public LiveData<List<ActionRecord>> searchRecords(String keyword) {
        return mActionRecordDAO.searchRecords(keyword);
    }




    public List<Excursion> getRelativeExcursions(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getRelativeExcursions(vacationID);
        });

        try{
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllExcursions;
    }


    public void insertLog(ActionRecord testLog) {
    }
}
