package com.joelforjava.music.service;

import com.joelforjava.music.model.Song;

/**
 * Handles the saving, loading, and updating of song data in a data store.
 *
 */
public interface SongDataService {

    /**
     * Add song data to the database.
     * @param song
     */
    void add(Song song);

    /**
     * Update the song data for a given song in the database.
     * @param song
     */
    void update(Song song);
}
