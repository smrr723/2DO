package com.example.scott.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by scott on 14/11/2017.
 */

public class MyMenu extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.add_task){
            Intent intent = new Intent(this, NewTaskActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
