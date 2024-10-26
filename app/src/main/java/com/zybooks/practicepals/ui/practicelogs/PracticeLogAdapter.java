package com.zybooks.practicepals.ui.practicelogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.practicepals.R;
import com.zybooks.practicepals.database.entities.PracticeLog;

import java.util.List;

public class PracticeLogAdapter extends RecyclerView.Adapter<PracticeLogAdapter.LogViewHolder> {

    private List<PracticeLog> practiceLogs;

    public PracticeLogAdapter(List<PracticeLog> logs) {
        this.practiceLogs = logs;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_practice_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        PracticeLog log = practiceLogs.get(position);
        holder.dateTextView.setText(log.getDate());
        holder.durationTextView.setText(log.getTimePracticed());
        holder.notesTextView.setText(log.getLogText());
    }

    @Override
    public int getItemCount() {
        return practiceLogs != null ? practiceLogs.size() : 0;
    }

    public void updateLogs(List<PracticeLog> newLogs) {
        this.practiceLogs = newLogs;
        notifyDataSetChanged();
    }

    static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, durationTextView, notesTextView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.log_date);
            durationTextView = itemView.findViewById(R.id.log_duration);
            notesTextView = itemView.findViewById(R.id.log_notes);
        }
    }
}
