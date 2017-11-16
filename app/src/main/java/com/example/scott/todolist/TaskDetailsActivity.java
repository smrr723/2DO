package com.example.scott.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TASK_NAME = "task_name";
    public static final String TASK_PRIORITY = "task_priority";
    public static final String TASK_DESC = "task_desc";
    public static final String TASK_DUE_DATE = "due_date";
    public static final String TASK_CATEGORY = "category";
    public static final String TASK_COMPLETED = "completed";
    public static final String TASK_ID = "task_id";
    public static final int REQUEST_CODE_EDIT = 10;
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
            final Task task = dbHelper.getTaskById(taskId);
            getSupportActionBar().setTitle(task.getName());
            taskName.setText(task.getName());
            taskDescription.setText(task.getDescription());
            taskPriority.setText(task.getPriority());
            taskDueDate.setText(task.getDateDue());
            taskCategory.setText(task.getCategory().toString());
            taskCompleted.setText(task.getCompleteStatus() == 1 ? "Completed" : "TODO");
            findViewById(R.id.btn_edit_todo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i =  new Intent(TaskDetailsActivity.this, NewTaskActivity.class);
                    i.putExtra(TASK_NAME, task.getName());
                    i.putExtra(TASK_DESC, task.getDescription());
                    i.putExtra(TASK_PRIORITY, task.getPriority());
                    i.putExtra(TASK_DUE_DATE, task.getDateDue());
                    i.putExtra(TASK_CATEGORY, task.getCategory().toString());
                    i.putExtra(TASK_COMPLETED, task.getCompleteStatus());
                    i.putExtra(TASK_ID, task.getId());
                    startActivityForResult(i, REQUEST_CODE_EDIT);
                }
            });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == REQUEST_CODE_EDIT) {
            finish();
        }
    }
}