package com.joelforjava.music.model

import spock.lang.Specification
import spock.lang.Unroll

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

    void 'MP3 file will identify itself as an MP3 encoded file'() {
        given: 'We have a file reference'
        File file = new File(classLoader.getResource('empty.mp3').file)

        when: 'We try to load it into a song object'
        Song song = new Song(file)

        then: 'The song will identify as an MP3'
        song.encodingType == 'mp3'

    }

    @Unroll("File #fileName has an encoding type of #expectedEncodingType")
    void 'Differently encoded files will identify as expected'() {
        given: 'We have a file reference'
        File file = new File(classLoader.getResource(fileName).file)

        when: 'We try to load it into a song object'
        Song song = new Song(file)

        then: 'The song will report the expected encoding type'
        song.encodingType == expectedEncodingType

        where:
        fileName         || expectedEncodingType
        'empty.mp3'      || 'mp3'
        'empty.flac'     || 'FLAC 16 bits'
        'empty.ogg'      || 'Ogg Vorbis v1'
        'empty.wma'      || 'ASF (audio): 0x0162 (Windows Media Audio 9 series (Professional))' // Yikes
        'empty-alac.m4a' || 'Apple Lossless'
    }

    @Unroll("File #fileName has a format of #expectedFormat")
    void 'Differently formatted files will identify as expected'() {
        given: 'We have a file reference'
        File file = new File(classLoader.getResource(fileName).file)

        when: 'We try to load it into a song object'
        Song song = new Song(file)

        then: 'The song will report the expected format'
        song.format == expectedFormat

        where:
        fileName         || expectedFormat
        'empty.mp3'      || 'MPEG-1 Layer 3'
        'empty.flac'     || 'FLAC 16 bits'
        'empty.ogg'      || 'Ogg Vorbis v1'
        'empty.wma'      || 'ASF (audio): 0x0162 (Windows Media Audio 9 series (Professional))' // Yikes
        'empty-alac.m4a' || 'Apple Lossless'
    }

}
