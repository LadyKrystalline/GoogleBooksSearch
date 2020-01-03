package com.example.googlebookssearch.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;

//This is a custom class, which represents a Book listing in the Google Books API.
public class Book implements Serializable {

    //Each Book object has: a cover image, title, author and publish Date.
    private final String coverImage;
    private final String title;
    private final String author;
    private final String publishDate;
    private final String description;


    public String getCoverImage() {
        return coverImage;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getDescription() {
        return description;
    }

    public Book(String coverImage, String title, String author, String publishDate, String description) {
        this.coverImage = coverImage;
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", title, author);
    }
}
