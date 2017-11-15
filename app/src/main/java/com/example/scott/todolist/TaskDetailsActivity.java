package com.example.scott.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TaskDetailsActivity extends AppCompatActivity {

        private DBHelper dbHelper;

        private TextView taskName;
        private TextView taskDescription;
        private TextView taskPriority;
        private TextView taskDueDate;
        private TextView taskCategory;
        private TextView taskCompleted;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_task_details);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            dbHelper = new DBHelper(this);

            taskName = (TextView) findViewById(R.id.taskName);
            taskDescription = (TextView) findViewById(R.id.taskDescription);
            taskPriority = (TextView) findViewById(R.id.taskPriority);
            taskDueDate = (TextView) findViewById(R.id.dueDate);
            taskCategory = (TextView) findViewById(R.id.category);
            taskCompleted = (TextView) findViewById(R.id.taskCompleted);
            if (getIntent() != null) {
                Integer taskId = getIntent().getIntExtra("TaskId", 0);
                Task task = dbHelper.getTaskById(taskId);
                getSupportActionBar().setTitle(task.getName());
                taskName.setText(task.getName());
                taskDescription.setText(task.getDescription());
                taskPriority.setText(task.getPriority());
                taskDueDate.setText(task.getDateDue());
                taskCategory.setText(task.getCategory().toString());
                taskCompleted.setText(task.getCompleteStatus() == 1 ? "Completed" : "TODO");
            }
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }

