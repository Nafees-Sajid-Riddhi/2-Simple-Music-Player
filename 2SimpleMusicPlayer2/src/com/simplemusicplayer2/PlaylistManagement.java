package com.simplemusicplayer2;

import java.util.List;

public interface PlaylistManagement {
    boolean isSongInPlaylist(String songId);
    void addSong(Song song);
    void removeSong(Song song);
    List<Song> getPlaylistSongs();
}
