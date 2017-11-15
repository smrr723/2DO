package com.example.scott.todolist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }



}
