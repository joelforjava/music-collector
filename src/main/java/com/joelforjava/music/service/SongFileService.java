package com.joelforjava.music.service;

import com.joelforjava.music.model.Song;

import java.util.List;

/**
 * Handles the loading of song data from a given file or files and the updating of song data to a given file or files.
 *
 */
public interface SongFileService {

    /**
     * Load the song data for a given song that exists at the given file path.
     *
     * @param filePath - a valid file path that points to a specific music file
     * @return the song data for the requested file.
     */
    Song load(String filePath);

    /**
     * Load the song data for all songs that exist in the given directory path.
     *
     * @param directoryPath - a valid file path that points to a directory of music files
     * @return the song data for all files within the provided directory path.
     */
    List<Song> loadAll(String directoryPath);

    /**
     * Update the tag data for a given song in the file system.
     *
     * @param song - a valid Song object that we wish to use to update a file.
     */
    void update(Song song);
}
