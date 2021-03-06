package com.example.scott.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity {

    EditText nameText;
    Spinner categoryText;
    Spinner prioritySpinner;
    EditText descriptionText;
    DBHelper dbHelper;
    Integer updateId;
    Integer completeStatus;

    Button addTaskButton;
    EditText dateDueTextView;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DBHelper(this);
        nameText = (EditText) findViewById(R.id.editName);
        categoryText = (Spinner) findViewById(R.id.categoryList);
        descriptionText = (EditText) findViewById(R.id.editDescription);
        prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner);
        addTaskButton = (Button) findViewById(R.id.addTask);
        dateDueTextView = (EditText) findViewById(R.id.dateDueTextView);
        myCalendar = Calendar.getInstance();
        dateDueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTaskActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            nameText.setText(extras.getString(TaskDetailsActivity.TASK_NAME));
            descriptionText.setText(extras.getString(TaskDetailsActivity.TASK_DESC));
            updateId = extras.getInt(TaskDetailsActivity.TASK_ID);
            completeStatus = extras.getInt(TaskDetailsActivity.TASK_COMPLETED);
            Log.d("hey", extras.getString(TaskDetailsActivity.TASK_DUE_DATE));
            // myCalendar.setTime(Da);
            // prioritySpinner.setSelection(getResources().getStringArray());

            String myFormat = "dd/MM/yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dateDueTextView.setText(extras.getString(TaskDetailsActivity.TASK_DUE_DATE));
            addTaskButton.setText(getString(R.string.update));
            addTaskButton.setTextSize(20f);
        }

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getExtras() == null) {
                    addTask();
                } else {
                    editTask();
                }
            }
        });
        loadCategories();
    }

    public void editTask() {
        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        Category category = (Category) categoryText.getSelectedItem();
        String priprity = prioritySpinner.getSelectedItem().toString();
        String dateDue = dateDueTextView.getText().toString();
        if (category == null) {
            Toast.makeText(this, "Please select task category", Toast.LENGTH_LONG).show();
            return;
        }
        Task task = new Task(updateId, name, category, description, completeStatus, priprity, dateDue);
        dbHelper.updateTask(task);
        setResult(RESULT_OK);
        finish();
    }

    public void addTask() {
        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        Category category = (Category) categoryText.getSelectedItem();
        String priprity = prioritySpinner.getSelectedItem().toString();
        String dateDue = dateDueTextView.getText().toString();
        if (category == null) {
            Toast.makeText(this, "Please select task category", Toast.LENGTH_LONG).show();
            return;
        }
        Task task = new Task(name, description, category, priprity, dateDue);
        dbHelper.addTask(task);
        finish();
    }

    private void loadCategories() {
        List<Category> categories = dbHelper.loadCategories();
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryText.setAdapter(categoryArrayAdapter);
        if (getIntent().getExtras() != null) {
            categoryText.setSelection(categories.indexOf(getIntent().getExtras().getString(TaskDetailsActivity.TASK_CATEGORY)));
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateDueTextView.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}