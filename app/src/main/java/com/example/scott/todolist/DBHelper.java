package com.example.scott.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by scott on 14/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "toDoList.db";
    public static final String TASKS_TABLE_NAME = "tasks";
    public static final String TASKS_COLUMN_ID = "id";
    public static final String TASKS_COLUMN_NAME = "name";
    public static final String TASKS_COLUMN_CATEGORY = "category";
    public static final String TASKS_COLUMN_DESCRIPTION = "description";
    public static final String TASKS_COLUMN_STATUS = "completeStatus";

    private HashMap hp;

    public DBHelper(Context context) { super(context, DATABASE_NAME, null, 1); }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + "(id INTEGER primary key autoincrement, name STRING, category STRING, description STRING )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TASKS_TABLE_NAME);
        onCreate(db);
    }
}
