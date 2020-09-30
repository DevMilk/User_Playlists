package com.trendyol.userplaylists.controller;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.trendyol.userplaylists.domain.Playlist;
import com.trendyol.userplaylists.domain.Track;
import com.trendyol.userplaylists.exceptions.NoTrackToDeleteException;
import com.trendyol.userplaylists.exceptions.ObjectAlreadyExistsException;
import com.trendyol.userplaylists.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    //O user_id için verilen playlist'i user'a kaydetsin
    @PostMapping("/")
    public ResponseEntity<Void> createPlaylist( String user_id, Playlist playlist) {
        playlist.setUser_id(user_id);
        playlistService.createPlaylist(playlist);

        //Return location of created playlist
        URI location = URI.create(String.format("/playlists/%s", playlist.getId()));
        return ResponseEntity.created(location).build();
    }

    // O kullanıcının tüm playlistlerini döndür.
    @PostMapping("/user/{user_id}")
    public ResponseEntity<List<Playlist>> getAllPlayList(@PathVariable String user_id) {
        return ResponseEntity.ok(playlistService.getAllPlaylists(user_id));
    }

    //Verilen playlist id'e sahip olan playlist'i döndür.
    @GetMapping("/{playlist_id}")
    public ResponseEntity<Playlist> getPlayList(@PathVariable String playlist_id) {
        try{

            Playlist result = playlistService.getPlayList(playlist_id);
            return ResponseEntity.ok(result);

        } catch(DocumentNotFoundException exception){
            return ResponseEntity.notFound().build();
        }


    }

    //Verilen playlist id'e sahip olan playlist'i sil.
    @DeleteMapping("/{playlist_id}")
    public ResponseEntity<Void> deletePlayList(@PathVariable String playlist_id) {
        try {

            playlistService.deletePlayList(playlist_id);
            return ResponseEntity.noContent().build();

        } catch(DocumentNotFoundException exception){
            return ResponseEntity.notFound().build();
        }
    }

    //ID'si verilen playlist'e track ekler
    @PostMapping("/tracks/{playlist_id}")
    public ResponseEntity<Void> addTrack(@PathVariable String playlist_id, Track track) {
        try {

            playlistService.addTrack(playlist_id, track);

            return ResponseEntity.ok().build();

        } catch(DocumentNotFoundException | ObjectAlreadyExistsException exception){

            //Eğer track zaten mevcutsa kod 409 döndür
            if(exception instanceof ObjectAlreadyExistsException)
                return ResponseEntity.status(HttpStatus.CONFLICT).build();

            return ResponseEntity.notFound().build();
        }
    }

    //ID'si verilen playlistten verilen track'ı çıkarsın
    @DeleteMapping("/tracks/{playlist_id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable String playlist_id, String track_name) {
        try {

            playlistService.deleteTrack(playlist_id, track_name);
            return ResponseEntity.noContent().build();

        } catch(DocumentNotFoundException | NoTrackToDeleteException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
