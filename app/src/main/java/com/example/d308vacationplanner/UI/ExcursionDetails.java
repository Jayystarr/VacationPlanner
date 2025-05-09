package com.example.d308vacationplanner.UI;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExcursionDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExcursionAdapter adapter;

    String name;

    private Excursion excursion;

    Excursion activeExcursion;
    int excursionID;
    int vacationID;

    Random rand = new Random();
    int numGen = rand.nextInt(99999);

    DatePickerDialog.OnDateSetListener excursionDate;
    final Calendar myCalendarDate = Calendar.getInstance();
    EditText editName;
    Repository repository;

    TextView editExcursionDate;

    String setDate;

    TextView textView4;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });








        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);











        excursion = (Excursion) getIntent().getSerializableExtra("excursion");

        repository = new Repository(getApplication());
        setContentView(R.layout.activity_excursion_details);
        name = getIntent().getStringExtra("title");
        editName = findViewById(R.id.excursionName);
        editName.setText(name);
        excursionID = getIntent().getIntExtra("id", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        setDate = getIntent().getStringExtra("excursionDate");
        numGen = rand.nextInt(99999);



        editName = findViewById(R.id.excursionName);
        editExcursionDate = findViewById(R.id.excursionDate);
        textView4 = findViewById(R.id.textView4);

        if (setDate != null) {
            try {
                Date excursionDate = sdf.parse(setDate);
                myCalendarDate.setTime(excursionDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateTheLabel();
            }
        };

        editExcursionDate = findViewById(R.id.excursionDate);
        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editExcursionDate.getText().toString();
                if (info.equals("")) info = setDate;
                try {
                    myCalendarDate.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendarDate
                        .get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH),
                        myCalendarDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }




    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursiondetails, menu);
        return true;
    }

    private void updateTheLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String formattedDate = sdf.format(myCalendarDate.getTime());
        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));


        editExcursionDate.setText(formattedDate);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }


        if (item.getItemId() == R.id.excursiondelete) {
            for (Excursion excursion : repository.getmAllExcursions()) {
                if (excursion.getExcursionID() == excursionID) activeExcursion = excursion;
            }
            repository.delete(activeExcursion);
            Toast.makeText(ExcursionDetails.this, activeExcursion.getExcursionName() + " was deleted",
                    Toast.LENGTH_LONG).show();
            List<Excursion> updatedExcursions = repository.getmAllExcursions();
            adapter.setmExcursions(updatedExcursions);
            ExcursionDetails.this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursionNotify) {
            String dateFromScreen = editExcursionDate.getText().toString();
            String alert = "Excursion " + name + " is today";

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
            intent.putExtra("key", alert);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, numGen, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            numGen = rand.nextInt(99999);
            System.out.println("numAlert Excursion = " + numGen);

            return true;
        }

        if (item.getItemId() == R.id.excursionsave) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String excursionDateString = sdf.format(myCalendarDate.getTime());
            Vacation vacation = null;

            List<Vacation> vacations = repository.getmRelativeVacations();

            if (vacations == null || vacations.isEmpty()) {
                Toast.makeText(this, "No vacations found", Toast.LENGTH_SHORT).show();
                return true;
            }

            for (Vacation vac : vacations) {
                if (vac.getVacationID() == vacationID) {
                    vacation = vac;
                    break;
                }
            }

            if (vacation == null) {
                Toast.makeText(this, "Vacation not found", Toast.LENGTH_SHORT).show();
                return true;
            }

            try {
                Date excursionDate = sdf.parse(excursionDateString);
                Date startDate = sdf.parse(vacation.getStartDate());
                Date endDate = sdf.parse(vacation.getEndDate());
                if (excursionDate.before(startDate) || excursionDate.after(endDate)) {
                    Toast.makeText(this, "Excursion Date must be within the Vacation's Start and End dates", Toast.LENGTH_LONG).show();
                    return true;
                } else {
                    Excursion excursion;


                    if (excursionID == -1) {
                        if (repository.getmAllExcursions().isEmpty()) excursionID = 0;
                        else
                            excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                        excursion = new Excursion(excursionID, editName.getText().toString(), vacationID, excursionDateString);
                        repository.insert(excursion);


                        List<Excursion> updatedExcursions = repository.getmAllExcursions();
                        ((ExcursionAdapter) recyclerView.getAdapter()).setmExcursions(updatedExcursions);
                        this.finish();
                    } else {
                        excursion = new Excursion(excursionID, editName.getText().toString(), vacationID, excursionDateString);
                        repository.update(excursion);


                        List<Excursion> updatedExcursions = repository.getmAllExcursions();
                        ((ExcursionAdapter) recyclerView.getAdapter()).setmExcursions(updatedExcursions);
                        this.finish();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return true;
        }





        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateTheLabel();
    }



}
