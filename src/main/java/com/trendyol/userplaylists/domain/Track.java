package com.trendyol.userplaylists.domain;


import lombok.NonNull;

public class Track {
    @NonNull
    private String name;

    private String length;
    private String artist;

    public Track(String name, String length, String artist) {
        this.name = name;
        this.length = length;
        this.artist = artist;
    }

    public Track() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
