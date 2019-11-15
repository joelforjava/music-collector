package com.joelforjava.music.model

import groovy.transform.EqualsAndHashCode
import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag

@EqualsAndHashCode
class Album {
    String title
    List<Song> tracks
    short numPieces // Number of discs, LPs, etc.
    Artist albumArtist
    String recordLabel // TODO - make a list?
    String barcode
    String catalogNumber
    // At this point, just to keep things somewhat simple, we'll assume a song release
    // date will also be the album release date
    String releaseDate
    String originalReleaseDate
    String albumCoverKey // Could be, for example, a UUID to a file in an S3 bucket or on local filesystem
    String releaseType
    String musicBrainzReleaseId

    Album(String title, Artist albumArtist) {
        this.title = title
        this.albumArtist = albumArtist
    }

    Album(AudioFile audioFile) {
        this(audioFile?.getTag())
    }

    Album(Tag tag) {
        Objects.requireNonNull(tag)
        final String albumArtistName = tag.hasField(FieldKey.ALBUM_ARTIST)
                ? tag.getFirst(FieldKey.ALBUM_ARTIST) : tag.getFirst(FieldKey.ARTIST)
        albumArtist = new Artist(name: albumArtistName)
        title = tag.getFirst(FieldKey.ALBUM)
        if (tag.hasField(FieldKey.DISC_TOTAL)) {
            numPieces = Short.parseShort(tag.getFirst(FieldKey.DISC_TOTAL))
        }
        releaseDate = tag.getFirst(FieldKey.YEAR) // Appears to be a UTC formatted date string - but not always!
        if (tag.hasField(FieldKey.ORIGINAL_YEAR)) {
            originalReleaseDate = tag.getFirst(FieldKey.ORIGINAL_YEAR)
        }
        if (tag.hasField(FieldKey.MUSICBRAINZ_RELEASEID)) {
            musicBrainzReleaseId = tag.getFirst(FieldKey.MUSICBRAINZ_RELEASEID)
        }
        if (tag.hasField(FieldKey.RECORD_LABEL)) {
            recordLabel = tag.getFirst(FieldKey.RECORD_LABEL)
        }
        if (tag.hasField(FieldKey.BARCODE)) {
            barcode = tag.getFirst(FieldKey.BARCODE)
        }
        if (tag.hasField(FieldKey.CATALOG_NO)) {
            catalogNumber = tag.getFirst(FieldKey.CATALOG_NO)
        }
        if (tag.hasField(FieldKey.MUSICBRAINZ_RELEASE_TYPE)) {
            releaseType = tag.getFirst(FieldKey.MUSICBRAINZ_RELEASE_TYPE)
        }
    }

    @Override
    String toString() {
        "$title by $albumArtist"
    }
}
