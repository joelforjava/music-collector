package com.joelforjava.music.model

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import spock.lang.Specification

class AlbumSpec extends Specification {

    private ClassLoader classLoader = this.getClass().classLoader

    void 'Creating a basic album object with an album name and artist'() {
        given: 'We have an album name and album artist'
        String albumName = 'Yer Favorites'
        String artist = 'Tragically Hip'

        when: 'We create an object using these values'
        Album album = new Album(albumName, new Artist(name: artist))

        then: 'It is created as expected'
        album
        album.title == albumName
    }

    void 'Creating a basic album object with an AudioFile'() {
        given: 'We have a file reference'
        File file = new File(classLoader.getResource('empty.mp3').file)

        and: 'We can use this reference to create an AudioFile'
        AudioFile audioFile = AudioFileIO.read(file)

        when: 'We create an album using this AudioFile object'
        Album album = new Album(audioFile)

        then: 'It is created as expected'
        album
        album.title == 'The Definitive MP3s'
    }
}
