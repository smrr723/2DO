package com.example.scott.todolist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ToDoListActivity extends MyMenu {

    private DBHelper dbHelper;
    private GridView categoriesView;
    private List<Category> categories;
    CategoryAdapter categoryAdapter;

    TextView totalTaskss;
    TextView completedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        dbHelper = new DBHelper(this);
        categoriesView = (GridView) findViewById(R.id.categories);
        categories = dbHelper.loadCategories();
        categories.add(0, new Category(null, "All tasks"));
        categoryAdapter = new CategoryAdapter(this, categories, dbHelper);
        categoriesView.setAdapter(categoryAdapter);
        categoriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ToDoListActivity.this, CategoryActivity.class);
                intent.putExtra("Category", categories.get(i).getId());
                startActivity(intent);
            }
        });

        totalTaskss = (TextView) findViewById(R.id.tasksCreated);
        completedTasks = (TextView) findViewById(R.id.tasksCompleted);

        findViewById(R.id.newTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoListActivity.this, NewTaskActivity.class);
                startActivity(intent);
            }
        });
    }

//    updates task counts for use on homepage

    private void reloadCounts() {
        categoryAdapter.notifyDataSetChanged();
        totalTaskss.setText(dbHelper.getCountByCategory(null).toString());
        completedTasks.setText(dbHelper.getCountOfCompletedTasks().toString());
    }

//    Uses built in onResume method, with reloadCounts, to refresh task quantities on homepage.

    @Override
    protected void onResume() {
        super.onResume();
        reloadCounts();
    }
}
