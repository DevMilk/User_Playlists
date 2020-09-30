package com.trendyol.userplaylists.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.trendyol.userplaylists.exceptions.ObjectAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlaylistTest {
    private Playlist testPlaylist;

    @BeforeEach
    public void setup(){
        testPlaylist = new Playlist();
    }
    @Test
    public void it_should_throw_ObjectAlreadyExistsException() throws Exception{
        Track trackToAdd = new Track("TestArtist","TestLength","TestName");
        testPlaylist.addTrack(trackToAdd);

        ObjectAlreadyExistsException exception = assertThrows(ObjectAlreadyExistsException.class,
                () -> {
                        testPlaylist.addTrack(trackToAdd);
                      }
              );

        assertEquals("Track already exists in playlist", exception.getMessage());
    }

    @Test
    public void it_should_increment_trackCount_after_track_added() throws Exception{
        Track trackToAdd = new Track("TestArtist","TestLength","TestName");

        testPlaylist.addTrack(trackToAdd);

        assertEquals(1,testPlaylist.getTrackCount());
    }

    @Test
    public void it_should_decrease_trackCount_after_track_deleted() throws Exception{
        Track trackToAdd = new Track("TestArtist","TestLength","TestName");
        testPlaylist.addTrack(trackToAdd);

        testPlaylist.deleteTrack(trackToAdd.getName());

        assertEquals(0,testPlaylist.getTrackCount());
    }
}
