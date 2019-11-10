package com.joelforjava.music.model

import spock.lang.Specification

class SongSpec extends Specification {

    private ClassLoader classLoader = this.getClass().classLoader

    void 'Song can be created using a File object'() {
        given: 'We have a file reference'
        File file = new File(classLoader.getResource('empty.mp3').file)

        when: 'We try to load it into a song object'
        Song song = new Song(file)

        then: 'It is created as expected'
        song.title != null
        song.title == 'Imma MP3'
    }
}
