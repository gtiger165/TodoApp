package com.hirarki.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by hp on 09/12/2018.
 */

public class TasksKhususAdapter extends RecyclerView.Adapter<TasksKhususAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Task> taskList;

    public TasksKhususAdapter(Context mCtx, List<Task> taskList){
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_task_khusus, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.textViewTanggalJam.setText(t.getTanggal()+" - "+t.getJam());
        holder.textViewKegiatan.setText(t.getTask());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Spinner spinnerKategori;
        TextView textViewTanggalJam, textViewKegiatan;

        public TasksViewHolder (View itemView){
            super(itemView);

            textViewTanggalJam = itemView.findViewById(R.id.textViewDateAndHours);
            textViewKegiatan = itemView.findViewById(R.id.textViewTask);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, EditJadwal.class);
            intent.putExtra("task", task);

            mCtx.startActivity(intent);
        }
    }
}
