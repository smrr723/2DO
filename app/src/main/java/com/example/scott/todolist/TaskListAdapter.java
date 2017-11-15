package com.example.scott.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by scott on 15/11/2017.
 */


public class TaskListAdapter extends BaseAdapter {
    private Context context;
    private List<Task> tasks = new ArrayList<>();
    private DBHelper dbHelper;

    public TaskListAdapter(Context context, List<Task> tasks, DBHelper dbHelper) {
        this.context = context;
        this.tasks = tasks;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.task_item, viewGroup, false);
        }
        final Task task = tasks.get(i);
        TextView taskName = (TextView) v.findViewById(R.id.taskName);
        TextView taskDescription = (TextView) v.findViewById(R.id.taskDescription);
        TextView dateDue = (TextView) v.findViewById(R.id.dateDue);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.isCompleted);
        taskName.setText(task.getName());
        taskDescription.setText(task.getDescription());
        dateDue.setText(task.getDateDue());
        checkBox.setChecked(task.getCompleteStatus() == 1);
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.changeStatus(task.getId(), checkBox.isChecked());
                tasks.get(i).setCompleteStatus(checkBox.isChecked() ? 1 : 0);
            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("TaskId", task.getId());
                context.startActivity(intent);
            }
        };
        taskName.setOnClickListener(onClickListener);
        taskDescription.setOnClickListener(onClickListener);
        dateDue.setOnClickListener(onClickListener);
        return v;
    }
}
