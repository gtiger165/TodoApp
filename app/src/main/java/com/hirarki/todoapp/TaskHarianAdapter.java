package com.hirarki.todoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hp on 10/12/2018.
 */

public class TaskHarianAdapter extends RecyclerView.Adapter<TaskHarianAdapter.TaskViewHolder> {

    private Context mCtx;
    private List<Task> taskList;

    public TaskHarianAdapter(Context mCtx, List<Task> taskList){
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_task_harian, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TaskViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.textViewJam.setText(t.getJam());
        holder.textViewTask.setText(t.getTask());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewJam, textViewTask;

        public TaskViewHolder(View itemView){
            super(itemView);

            textViewJam = itemView.findViewById(R.id.textViewHours);
            textViewTask = itemView.findViewById(R.id.textViewTask);

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
