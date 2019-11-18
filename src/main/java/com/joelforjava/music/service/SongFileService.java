package com.joelforjava.music.service;

import com.joelforjava.music.model.Song;

import java.util.List;

/**
 * Handles the loading of song data from a given file or files and the updating of song data to a given file or files.
 *
 */
public interface SongFileService {

    /**
     * Read the song data for a given song that exists at the given file path.
     *
     * @param filePath - a valid file path that points to a specific music file
     * @return the song data for the requested file.
     */
    Song readFile(String filePath);

    /**
     * Read the song data for all songs that exist in the given directory path.
     *
     * @param directoryPath - a valid file path that points to a directory of music files
     * @return the song data for all files within the provided directory path.
     */
    List<Song> readDirectory(String directoryPath);

}
