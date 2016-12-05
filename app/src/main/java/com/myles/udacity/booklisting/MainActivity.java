package com.myles.udacity.booklisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Book book1 = new Book(
                "Book 1 Title",
                new String[]{"Author"},
                "ORelly",
                new Date()
        );
        Book book2 = new Book(
                "Book 2 Title",
                new String[]{"Author"},
                "ORelly",
                new Date()
        );
        Book book3 = new Book(
                "Book 3 Title",
                new String[]{"Author"},
                "ORelly",
                new Date()
        );

        ArrayList<Book> books = new ArrayList<Book>();
        books.add(book1);
        books.add(book2);
        books.add(book3);

        BookAdapter adapter = new BookAdapter(this, books);
        ((ListView)this.findViewById(R.id.list)).setAdapter(adapter);
    }
}
