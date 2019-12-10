package com.example.googlebookssearch;

import android.support.annotation.NonNull;

import java.io.Serializable;

//This is a custom class, which represents a Book listing in the Google Books API.
public class Book implements Serializable {
    //Each Book object has: a title, author and publish Date.
    private final String title;
    private final String author;
    private final String publishDate;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public Book(String title, String author, String publishDate) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", title, author);
    }
}
