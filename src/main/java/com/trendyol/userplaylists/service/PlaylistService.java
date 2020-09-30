package com.trendyol.userplaylists.service;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.trendyol.userplaylists.domain.Playlist;
import com.trendyol.userplaylists.domain.Track;
import com.trendyol.userplaylists.exceptions.NoTrackToDeleteException;
import com.trendyol.userplaylists.exceptions.ObjectAlreadyExistsException;
import com.trendyol.userplaylists.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository repository;

    public PlaylistService(PlaylistRepository repository) {
        this.repository = repository;
    }

    public void createPlaylist(Playlist newPlayList){
        repository.insert(newPlayList);
    }
    public List<Playlist> getAllPlaylists(String user_id){
        return repository.findAllByUserId(user_id);
    }

    public Playlist getPlayList(String id) throws DocumentNotFoundException{
        return repository.findById(id);
    }

    public void deletePlayList(String id) throws DocumentNotFoundException{
        repository.deletePlaylist(id);
    }

    public void addTrack(String playlistId, Track trackToAdd) throws DocumentNotFoundException,ObjectAlreadyExistsException{
            Playlist playlistToChange = repository.findById(playlistId);
            playlistToChange.addTrack(trackToAdd);
            repository.update(playlistToChange);

    }

    public void deleteTrack(String playlistId, String trackNameToDelete ) throws DocumentNotFoundException, NoTrackToDeleteException {
        Playlist playlistToChange = repository.findById(playlistId);
        if(!playlistToChange.deleteTrack(trackNameToDelete))
            throw new NoTrackToDeleteException("Track to be deleted not found in given playlist");
        repository.update(playlistToChange);
    }
}
