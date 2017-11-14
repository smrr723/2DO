package com.example.scott.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.scott.todolist.DBHelper.TASKS_COLUMN_CATEGORY;
import static com.example.scott.todolist.DBHelper.TASKS_COLUMN_DESCRIPTION;
import static com.example.scott.todolist.DBHelper.TASKS_COLUMN_ID;
import static com.example.scott.todolist.DBHelper.TASKS_COLUMN_NAME;
import static com.example.scott.todolist.DBHelper.TASKS_COLUMN_STATUS;
import static com.example.scott.todolist.DBHelper.TASKS_TABLE_NAME;

/**
 * Created by scott on 12/11/2017.
 */

public class Task {
    private Integer id;
    private String name;
    private String category;
    private String description;
    private Integer completeStatus;

    public Task(Integer id, String name, String category, String description, Integer completeStatus) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.completeStatus = 0;
    }
    public Task(Integer id, String name, String description, Integer completeStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.completeStatus = 0;

    }

//    CRUD Methods/Functions

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCompleteStatus() {
        return completeStatus;
    }

    public void setCompleteStatus(Integer completeStatus) {
        this.completeStatus = completeStatus;
    }

    public static ArrayList<Task> all(DBHelper dbHelper){
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_NAME));
            String category = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_CATEGORY));
            String description = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_DESCRIPTION));
            Integer completeStatus = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_STATUS));
            Task task = new Task(id, name, category, description, completeStatus);
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    public boolean save(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASKS_COLUMN_NAME, this.name);
        cv.put(TASKS_COLUMN_CATEGORY, this.category);
        cv.put(TASKS_COLUMN_DESCRIPTION, this.description);
        cv.put(TASKS_COLUMN_STATUS, this.completeStatus);
        return true;
    }

    public static boolean delete(DBHelper dbHelper, Integer id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = " id = ?";
        String[] values = {id.toString()};
        db.delete(TASKS_TABLE_NAME, selection, values);
        return true;
    }

}

