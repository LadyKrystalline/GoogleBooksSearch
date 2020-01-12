package com.example.googlebookssearch.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Video implements Serializable {
     String title;
     String channel;
     String releaseDate;
     String videoLength;

     public String getTitle() {
          return title;
     }

     public String getChannel() {
          return channel;
     }

     public String getReleaseDate() {
          return releaseDate;
     }

     public String getVideoLength() {
          return videoLength;
     }

     public Video(String title, String channel, String releaseDate, String videoLength) {
          this.title = title;
          this.channel = channel;
          this.releaseDate = releaseDate;
          this.videoLength = videoLength;
     }

     @NonNull
     @Override
     public String toString() {
          return super.toString();
     }
}
