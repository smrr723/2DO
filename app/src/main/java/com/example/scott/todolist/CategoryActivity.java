package com.example.scott.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class CategoryActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
//        for back button, getActionBar causes crashing for some reason
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
//    function for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}