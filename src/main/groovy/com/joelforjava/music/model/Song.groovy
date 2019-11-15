package com.joelforjava.music.model

import org.jaudiotagger.audio.AudioFile
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.AudioHeader
import org.jaudiotagger.audio.exceptions.CannotReadException
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.TagException

import java.nio.file.Path
import java.nio.file.Paths

class Song {
    String title
    Short trackNumber
    Short discNumber
    Short discTotal
    Artist artist
    Album album
    String releaseDate
    String originalReleaseDate // Could be the same as releaseDate.
    int releaseYear
    int originalReleaseYear
    int length
    String genre
    String musicBrainzTrackId
    String encodingType
    String format
    String bitRate
    boolean lossless
    boolean variableBitRate
    String windowsFileUrl
    String posixFileUrl

    Song(String filePath) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(Paths.get(filePath))
    }

    Song(Path path) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(path.toFile())
    }

    Song(File file) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(AudioFileIO.read(file))
    }

    Song(AudioFile audioFile) {
        Objects.requireNonNull(audioFile)
        final Tag tag = audioFile.getTag()
        title = tag.getFirst(FieldKey.TITLE)
        if (tag.hasField(FieldKey.TRACK)) {
            trackNumber = Short.parseShort(tag.getFirst(FieldKey.TRACK))
        }
        if (tag.hasField(FieldKey.DISC_NO)) {
            discNumber = Short.parseShort(tag.getFirst(FieldKey.DISC_NO))
        }
        if (tag.hasField(FieldKey.DISC_TOTAL)) {
            discTotal = Short.parseShort(tag.getFirst(FieldKey.DISC_TOTAL))
        }
        artist = new Artist(name: tag.getFirst(FieldKey.ARTIST))
        final String albumArtistName = tag.getFirst(FieldKey.ALBUM_ARTIST)
        final String albumName = tag.getFirst(FieldKey.ALBUM)
        album = new Album(albumName, albumArtistName)
        releaseDate = tag.getFirst(FieldKey.YEAR) // Appears to be a UTC formatted date string - but not always!
        if (tag.hasField(FieldKey.ORIGINAL_YEAR)) {
            originalReleaseDate = tag.getFirst(FieldKey.ORIGINAL_YEAR)
        }
        genre = String.join(",", tag.getAll(FieldKey.GENRE))
        musicBrainzTrackId = tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID)

        AudioHeader audioHeader = audioFile.getAudioHeader()
        length = audioHeader.getTrackLength()
        encodingType = audioHeader.getEncodingType()
        format = audioHeader.getFormat()
        bitRate = audioHeader.getBitRate()
        lossless = audioHeader.isLossless()
        variableBitRate = audioHeader.isVariableBitRate()

        try {
            // TODO - figure this part out. Is it possible to get Windows URI while running in *nix?
            //      - even if it's just an approximation?
            // What do we do in the case that we're running this in both Windows and *nix?
            File fileRef = audioFile.getFile()
            windowsFileUrl = fileRef.getCanonicalPath()
            posixFileUrl = fileRef.getCanonicalPath()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    @Override
    String toString() {
        "$title by $artist"
    }
}
