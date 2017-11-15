package com.example.scott.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.taskList);
        Intent intent = getIntent();
        if (intent != null) {

            Integer categoryId = intent.getIntExtra("Category", 0);
            if (categoryId == 0) {
                categoryId = null;
            }
            Category category = dbHelper.getByCategoryId(categoryId);
            if (category == null) {
                getSupportActionBar().setTitle("All tasks");
            } else {
                getSupportActionBar().setTitle("Category: " + category.getName());
            }
            TaskListAdapter taskListAdapter = new TaskListAdapter(this, dbHelper.getTasksByCategory(categoryId), dbHelper);
            listView.setAdapter(taskListAdapter);

        }
    }
}