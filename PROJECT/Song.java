package com.example.myapplication;

public class Song {

    private String title;
    private String artist;
    private String url;
    private int duration;

    // Constructor
    public Song(String title, String artist, String url, int duration) {
        this.title = title;
        this.artist = artist;
        this.url = url;
        this.duration = duration;
    }

    // Getter and Setter methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
