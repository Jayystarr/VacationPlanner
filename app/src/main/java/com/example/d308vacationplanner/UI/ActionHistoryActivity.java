package com.example.d308vacationplanner.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.ActionRecord;

import java.util.ArrayList;
import java.util.List;

public class ActionHistoryActivity extends AppCompatActivity {

    private List<ActionRecord> allRecords = new ArrayList<>();
    private EditText searchInput;

    private ActionRecordViewModel recordViewModel;
    private RecordAdapter recordAdapter;
    private List<ActionRecord> filteredRecords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewLogs);
        searchInput = findViewById(R.id.searchLogs);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new RecordAdapter(filteredRecords);
        recyclerView.setAdapter(recordAdapter);

        recordViewModel = new ViewModelProvider(this).get(ActionRecordViewModel.class);
        recordViewModel.getAllRecords().observe(this, records -> {
            allRecords.clear();
            allRecords.addAll(records);
            filterRecords("");
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRecords(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterRecords(String query) {
        filteredRecords.clear();
        for (ActionRecord record : allRecords) {
            if (record.getTripTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredRecords.add(record);
            }
        }
        recordAdapter.notifyDataSetChanged();
    }
}