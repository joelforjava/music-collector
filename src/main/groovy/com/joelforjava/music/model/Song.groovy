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
        // TODO - move artist-specific initialization to Artist
        artist = new Artist(name: tag.getFirst(FieldKey.ARTIST))
        album = new Album(tag)

        releaseDate = tag.getFirst(FieldKey.YEAR) // Appears to be a UTC formatted date string - but not always!
        if (tag.hasField(FieldKey.ORIGINAL_YEAR)) {
            originalReleaseDate = tag.getFirst(FieldKey.ORIGINAL_YEAR)
        }
        genre = String.join(",", tag.getAll(FieldKey.GENRE))
        musicBrainzTrackId = tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID)

        AudioHeader audioHeader = audioFile.audioHeader
        length = audioHeader.trackLength
        encodingType = audioHeader.encodingType
        format = audioHeader.format
        bitRate = audioHeader.bitRate
        lossless = audioHeader.lossless
        variableBitRate = audioHeader.variableBitRate

        try {
            // What do we do in the case that we're running this in both Windows and *nix at different times?
            // It will be an issue if we ever want to save data back to the files
            // or if we want to do something like copy a file
            File fileRef = audioFile.getFile()
            String os = System.getProperty('os.name')
            if (os.indexOf('win') > 0) {
                windowsFileUrl = fileRef.getCanonicalPath()
            } else {
                posixFileUrl = fileRef.getCanonicalPath()
            }
        } catch (IOException e) {
            println("Could not get file path details from $audioFile") // TODO - change to log
            e.printStackTrace()
        }
    }

    @Override
    String toString() {
        "$title by $artist"
    }

    enum EncodingType {
        ALAC, FLAC, WAV, MP3, AAC, OGG, WMA
    }
}
