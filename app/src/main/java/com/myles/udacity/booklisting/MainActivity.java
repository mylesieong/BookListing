package com.myles.udacity.booklisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Book book= new Book(
                "Mock book",
                new String[]{"Myles", "Gene", "Anderson"},
                "ORelly",
                new Date(20161022)
        );
        Log.v("Myles Debug", book.toString());
    }
}
