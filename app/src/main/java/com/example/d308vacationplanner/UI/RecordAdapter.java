package com.example.d308vacationplanner.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.ActionRecord;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private List<ActionRecord> records = new ArrayList<>();

    public RecordAdapter(List<ActionRecord> recordList) {
        this.records = recordList;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_item, parent, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        ActionRecord currentRecord = records.get(position);
        holder.textViewDescription.setText(currentRecord.getDescription());
        holder.textViewTripTitle.setText(currentRecord.getTripTitle());
        holder.textViewDateRecorded.setText(currentRecord.getDateRecorded());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public void setRecords(List<ActionRecord> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTripTitle;
        private final TextView textViewDateRecorded;
        private final TextView textViewDescription;

        public RecordViewHolder(View itemView) {
            super(itemView);
            textViewDateRecorded = itemView.findViewById(R.id.textViewDateRecorded);
            textViewTripTitle = itemView.findViewById(R.id.textViewTripTitle);
            textViewDescription = itemView.findViewById(R.id.textViewAction);
        }
    }
}
