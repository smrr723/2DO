package com.example.scott.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    public static final String TASKS_COLUMN_PRIORITY = "priority";
    public static final String TASKS_COLUMN_DATE_DUE = "dateDue";

    public static final String CATEGORIES_TABLE_NAME = "categories";
    public static final String CATEGORIES_COLUMN_ID = "id";
    public static final String CATEGORIES_COLUMN_NAME = "name";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 7);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + "(id INTEGER primary key autoincrement, name STRING, category INTEGER, description STRING, completeStatus INTEGER, priority STRING, dateDue String )");
        db.execSQL("CREATE TABLE " + CATEGORIES_TABLE_NAME + "(id INTEGER primary key autoincrement, name STRING)");
        addCategory(db, "Errands");
        addCategory(db, "Work projects");
        addCategory(db, "College");
        addCategory(db, "Work");
        addCategory(db, "Misc");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES_TABLE_NAME);
        onCreate(db);
    }

    public synchronized void addTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKS_COLUMN_NAME, task.getName());
        contentValues.put(TASKS_COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(TASKS_COLUMN_CATEGORY, task.getCategory().getId());
        contentValues.put(TASKS_COLUMN_STATUS, 0);
        contentValues.put(TASKS_COLUMN_PRIORITY, task.getPriority());
        contentValues.put(TASKS_COLUMN_DATE_DUE, task.getDateDue());
        database.insertOrThrow(TASKS_TABLE_NAME, null, contentValues);
        database.close();

    }

    public synchronized void updateTask(Task task) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASKS_COLUMN_NAME, task.getName());
        contentValues.put(TASKS_COLUMN_DESCRIPTION, task.getDescription());
        contentValues.put(TASKS_COLUMN_CATEGORY, task.getCategory().getId());
        contentValues.put(TASKS_COLUMN_STATUS, task.getCompleteStatus());
        contentValues.put(TASKS_COLUMN_PRIORITY, task.getPriority());
        contentValues.put(TASKS_COLUMN_DATE_DUE, task.getDateDue());
        Log.d("Update", "Update Item " + task.getId());
        database.update(TASKS_TABLE_NAME, contentValues, "id=?", new String[]{task.getId().toString()});
        database.close();
    }
    public synchronized Task getTaskById(Integer taskId) {
        String query = "SELECT * FROM " + TASKS_TABLE_NAME + " WHERE id=" + taskId;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_DESCRIPTION));
            Integer categoryId = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_CATEGORY));

            Category category = getByCategoryId(categoryId);
            Integer completeStatus = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_STATUS));
            String priority = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_PRIORITY));
            String dateDue = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_DATE_DUE));
            return new Task(id, name, category, description, completeStatus, priority, dateDue);
        }
        return null;
    }


    public synchronized void addCategory(SQLiteDatabase database, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORIES_COLUMN_NAME, name);
        database.insertOrThrow(CATEGORIES_TABLE_NAME, null, contentValues);
    }

    public synchronized Category getByCategoryId(Integer categoryId) {
        String query = "SELECT * FROM " + CATEGORIES_TABLE_NAME + " WHERE id=" + categoryId;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(CATEGORIES_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CATEGORIES_COLUMN_NAME));
            return new Category(id, name);
        }
        return null;
    }
//        Probably won't make a different with this app, but synchronized used to avoid crashing from memory consistency issues
//        because SQLite doesn't support multi-threading (basically doing to things at once(?)), so synch methods makes them go in sequence.

    public synchronized Long getCountByCategory(Integer categoryId) {
        SQLiteDatabase db = getReadableDatabase();

//        Using LONG below, using OL, because queryNumEntries won't accept Integers.

        Long count = 0L;
        if (categoryId == null) {
            count = DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME);
        } else {
            count = DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME, "category=?", new String[]{categoryId.toString()});
        }
        db.close();
        return count;
    }

    public synchronized Long getCountOfCompletedTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Long count = DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME, "completeStatus=1", null);
        db.close();
        return count;
    }


    public synchronized List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CATEGORIES_TABLE_NAME;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                Category category = new Category(id, name);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        database.close();
        return categories;

    }

    public synchronized List<Task> getTasksByCategory(Integer categoryId) {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (categoryId == null) {
            cursor = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);

        } else {
            cursor = db.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME + " WHERE category=" + categoryId, null);
        }
        if (cursor.moveToFirst()) {
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_DESCRIPTION));
                Integer completeStatus = cursor.getInt(cursor.getColumnIndex(TASKS_COLUMN_STATUS));
                String priority = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_PRIORITY));
                String dateDue = cursor.getString(cursor.getColumnIndex(TASKS_COLUMN_DATE_DUE));
                Task task = new Task(id, name, null, description, completeStatus, priority, dateDue);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                try {
                    Date date1 = sdf.parse(t1.getDateDue());
                    Date date2 = sdf.parse(t2.getDateDue());
                    return date1.compareTo(date2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return tasks;
    }


    public synchronized void changeStatus(Integer taskId, boolean completed) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Integer isCompleted = completed ? 1 : 0;
        contentValues.put(TASKS_COLUMN_STATUS, isCompleted);
        int didUpdate = db.update(TASKS_TABLE_NAME, contentValues, "id=?", new String[]{taskId.toString()});
        db.close();
    }

    public boolean delete(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "id=?";
        Log.d("Delete", "Delete Item " + id);
        String[] values = {id.toString()};
        db.delete(TASKS_TABLE_NAME, selection, values);
        return true;
    }
}



