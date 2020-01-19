package com.example.googlebookssearch.objects;

import androidx.annotation.NonNull;

import java.io.Serializable;
/*
 * Resource Representation: https://developers.google.com/youtube/v3/docs/search#resource
 * GET: https://www.googleapis.com/youtube/v3/search
 */

public class YTVideo implements Serializable {

    String thumbnail;
    String title;
    String channel;
    String releaseDate;
    String videoLength;

    public String getThumbnail() {
        return thumbnail;
    }

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

    public YTVideo(String thumbnail, String title, String channel, String releaseDate, String videoLength) {
        this.thumbnail = thumbnail;
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
