package com.example.scott.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class NewTaskActivity extends AppCompatActivity {

    EditText nameText;
    Spinner categoryText;
    EditText descriptionText;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        nameText = (EditText)findViewById(R.id.editName);
        categoryText = (Spinner) findViewById(R.id.categoryList);
        descriptionText = (EditText)findViewById(R.id.editDescription);
    }

    public void addTask(View button){
        dbHelper = new DBHelper(this);
        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        Integer completeStatus = 0;
//      need to add categoryText in here but need to use spinner
//      need id
        Task task = new Task(name, description, completeStatus);
        task.save(dbHelper);
        Intent intent = new Intent(this, ToDoListActivity.class);
        startActivity(intent);

    }
}
