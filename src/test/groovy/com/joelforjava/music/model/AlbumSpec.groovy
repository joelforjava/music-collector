package com.joelforjava.music.model

import spock.lang.Specification

class AlbumSpec extends Specification {

    void 'Creating a basic album object'() {
        given: 'We have an album name and album artist'
        String albumName = 'Yer Favorites'
        String artist = 'Tragically Hip'

        when: 'We create an object using these values'
        Album album = new Album(albumName, new Artist(name: artist))

        then: 'It is created as expected'
        album
        album.title == albumName
    }
}
