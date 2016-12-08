package com.myles.udacity.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by asus on 5/12/2016.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        }

        Book currentBook = this.getItem(position);

        ((TextView) listItemView.findViewById(R.id.text_title)).setText(currentBook.getTitle());
        ((TextView) listItemView.findViewById(R.id.text_publisher)).setText(currentBook.getPublisher());
        ((TextView) listItemView.findViewById(R.id.text_publish_date)).setText(currentBook.getPublishDate());
        StringBuilder authorsBuilder = new StringBuilder();
        for (int i = 0; i < currentBook.getAuthors().length; i++) {
            authorsBuilder.append(currentBook.getAuthors()[i]);
            authorsBuilder.append(" ");
        }
        ((TextView) listItemView.findViewById(R.id.text_authors)).setText(authorsBuilder.toString());

        return listItemView;
    }
}
