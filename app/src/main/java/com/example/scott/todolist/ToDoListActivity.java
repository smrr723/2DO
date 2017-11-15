package com.example.scott.todolist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ToDoListActivity extends MyMenu {

//    private static int SPLASH_TIME_OUT = 4000;

    private TextView allTaskQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
//        allTaskQty = (TextView)findViewById(R.id.all_task_qty);
//        allTaskQty.setText(); SET ALL TASKS QTY ON HOMEPAGE.

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent homeIntent = new Intent(ToDoListActivity.this, HomeActivity.class);
//                startActivity(homeIntent);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }



}
