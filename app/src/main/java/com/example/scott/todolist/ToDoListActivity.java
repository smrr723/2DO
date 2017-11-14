package com.example.scott.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ToDoListActivity extends MyMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
    }
}
