package com.trendyol.userplaylists.repository;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.query.QueryResult;
import com.trendyol.userplaylists.domain.Playlist;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PlaylistRepository {
    private final Cluster couchbaseCluster;
    private final Collection playListCollection;

    public PlaylistRepository(Cluster couchbaseCluster, Collection playListCollection) {
        this.couchbaseCluster = couchbaseCluster;
        this.playListCollection = playListCollection;
    }

    public void insert(Playlist playlist) {
        playListCollection.insert(playlist.getId(), playlist);
    }

    public void update(Playlist playlist) {
        playListCollection.replace(playlist.getId(), playlist);
    }

    public Playlist findById(String id) throws DocumentNotFoundException{
        GetResult getResult = playListCollection.get(id);
        Playlist playlist = getResult.contentAs(Playlist.class);
        return playlist;
    }

    public List<Playlist> findAllByUserId(String user_id) {
        String statement = "Select * from playlist where user_id = '"+user_id+"'";
        QueryResult query = couchbaseCluster.query(statement);
        List<Playlist> result = query.rowsAs(Playlist.class);
        return result;
    }

    public void deletePlaylist(String playlistId) throws DocumentNotFoundException{
        playListCollection.remove(playlistId);
    }


}