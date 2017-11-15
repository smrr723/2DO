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
    private Category category;
    private String description;
    private Integer completeStatus;
    private String priority;
    private String dateDue;


    public Task(Integer id, String name, Category category, String description, Integer completeStatus, String priority, String dateDue) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.completeStatus = completeStatus;
        this.priority = priority;
        this.dateDue = dateDue;
    }

    public Task(String name, String description, Category category, String priority, String dateDue) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.completeStatus = 0;
        this.priority = priority;
        this.dateDue = dateDue;
    }

//    CRUD Methods/Functions

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
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

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateDue() {
        return dateDue;
    }
}

