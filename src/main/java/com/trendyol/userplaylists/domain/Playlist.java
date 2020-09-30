package com.trendyol.userplaylists.domain;


import com.trendyol.userplaylists.exceptions.ObjectAlreadyExistsException;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.UUID;

public class Playlist {

    private final String id = UUID.randomUUID().toString();

    @NonNull
    private String name;
    private String desc;
    private int followersCount;

    private ArrayList<Track> tracks = new ArrayList<>();
    private int trackCount = 0;

    private String user_id;

    public Playlist(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Playlist() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    //Delete track from tracks list and return if it is deleted or not
    public Boolean deleteTrack(String trackNameToDelete){
        Boolean isDeleted = false;
        for(Track track: tracks ){
            if(track.getName().equals(trackNameToDelete) && tracks.remove(track)){
                //Decrease trackCount after track removed
                trackCount--;
                return true;
            }
        }
        return false;
    }

    //Check if track exists in tracks list
    private Boolean IsTrackExists(Track trackToCheck){
            for(Track track: tracks){
                if(track.getName().equals(trackToCheck.getName())){
                    return true;
                }
            }
            return false;
    }

    //Add track to tracks
    public void addTrack(Track trackToAdd) throws ObjectAlreadyExistsException {
        if(IsTrackExists(trackToAdd)) throw new ObjectAlreadyExistsException("Track already exists in playlist");
        //If track added increment trackCount
        if(tracks.add(trackToAdd)) trackCount++;
    }
}
