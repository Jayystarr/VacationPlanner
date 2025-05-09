package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Vacations extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacations2);
        FloatingActionButton floatAction=findViewById(R.id.floatingActionButton2);
        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Vacations.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmRelativeVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);




    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_excursions, menu);
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Vacation> allVacations = repository.getmRelativeVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){

            if (item.getItemId() == android.R.id.home) {
                this.finish();
                return true;
            }




        if(item.getItemId()==R.id.add){
            repository = new Repository(getApplication());
            Vacation vacation = new Vacation(1, "Spain", "Mariott", "03/25/25", "04/24/25");
            repository.insert(vacation);
            vacation = new Vacation(2, "Chicago", "Hilton", "11/01/25", "12/12/25");
            repository.insert(vacation);
            Excursion excursion = new Excursion(1, "Zoo Visit", 2, "11/26/25");
            repository.insert(excursion);
            excursion = new Excursion(0, "GoCart Racing", 1, "03/26/25");
            repository.insert(excursion);
            this.finish();
            excursion = new Excursion(0, "ZipLining", 0, "");
            repository.insert(excursion);
            this.finish();
            return true;
        }

        else if (item.getItemId() == R.id.logreports){
            Intent intent = new Intent(this, ActionHistoryActivity.class);
            startActivity(intent);
        }

        return true;


    }






}