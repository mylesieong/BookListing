package com.myles.udacity.booklisting;

import java.util.Date;

/**
 * Created by asus on 5/12/2016.
 */

public class Book {
    private String mTitle;
    private String[] mAuthors;
    private String mPublisher;
    private String mPublishDate;

    public Book() {
        super();
    }

    public Book(String title) {
        this.mTitle = title;
    }

    public Book(String title, String[] authors, String publisher, String publishDate) {
        this.mTitle = title;
        this.mAuthors = authors;
        this.mPublisher = publisher;
        this.mPublishDate = publishDate;
    }

    /**
     * Setters
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setAuthors(String[] authors) {
        this.mAuthors = authors;
    }

    public void setPublisher(String publisher) {
        this.mPublisher = publisher;
    }

    public void setPublishDate(String publishDate) {
        this.mPublishDate = publishDate;
    }

    /**
     * Getters
     */
    public String getTitle() {
        return this.mTitle;
    }

    public String[] getAuthors() {
        return this.mAuthors;
    }

    public String getPublisher() {
        return this.mPublisher;
    }

    public String getPublishDate() {
        return this.mPublishDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().toString());
        sb.append(this.mTitle);
        sb.append("/");
        for (int i = 0; i < this.mAuthors.length; i++) {
            sb.append(this.mAuthors[i]);
            sb.append("/");
        }
        sb.append(this.mPublisher);
        sb.append("/");
        sb.append(this.mPublishDate);
        sb.append("#");
        return sb.toString();
    }
}
