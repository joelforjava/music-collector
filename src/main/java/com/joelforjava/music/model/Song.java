package com.joelforjava.music.model;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Song {
    private String title;
    private Short trackNumber;
    private Short discNumber;
    private Short discTotal;
    private String artistName;
    private Album album;
    private String releaseDate;
    private String originalReleaseDate; // Could be the same as releaseDate.
    private int releaseYear;
    private int originalReleaseYear;
    private int length;
    private String genre;
    private String musicBrainzTrackId;
    private String encodingType;
    private String format;
    private String bitRate;
    private boolean lossless;
    private boolean variableBitRate;
    private String windowsFileUrl;
    private String posixFileUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Short getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Short trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Short getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Short discNumber) {
        this.discNumber = discNumber;
    }

    public Short getDiscTotal() {
        return discTotal;
    }

    public void setDiscTotal(Short discTotal) {
        this.discTotal = discTotal;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalReleaseDate() {
        return originalReleaseDate;
    }

    public void setOriginalReleaseDate(String originalReleaseDate) {
        this.originalReleaseDate = originalReleaseDate;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getOriginalReleaseYear() {
        return originalReleaseYear;
    }

    public void setOriginalReleaseYear(int originalReleaseYear) {
        this.originalReleaseYear = originalReleaseYear;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMusicBrainzTrackId() {
        return musicBrainzTrackId;
    }

    public void setMusicBrainzTrackId(String musicBrainzTrackId) {
        this.musicBrainzTrackId = musicBrainzTrackId;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public boolean isLossless() {
        return lossless;
    }

    public void setLossless(boolean lossless) {
        this.lossless = lossless;
    }

    public boolean isVariableBitRate() {
        return variableBitRate;
    }

    public void setVariableBitRate(boolean variableBitRate) {
        this.variableBitRate = variableBitRate;
    }

    public String getWindowsFileUrl() {
        return windowsFileUrl;
    }

    public void setWindowsFileUrl(String windowsFileUrl) {
        this.windowsFileUrl = windowsFileUrl;
    }

    public String getPosixFileUrl() {
        return posixFileUrl;
    }

    public void setPosixFileUrl(String posixFileUrl) {
        this.posixFileUrl = posixFileUrl;
    }

    public Song(String filePath) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(Paths.get(filePath));
    }

    public Song(Path path) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(path.toFile());
    }

    public Song(File file) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        this(AudioFileIO.read(file));
    }

    public Song(AudioFile audioFile) {
        Objects.requireNonNull(audioFile);
        final Tag tag = audioFile.getTag();
        title = tag.getFirst(FieldKey.TITLE);
        if (tag.hasField(FieldKey.TRACK)) {
            trackNumber = Short.parseShort(tag.getFirst(FieldKey.TRACK));
        }
        if (tag.hasField(FieldKey.DISC_NO)) {
            discNumber = Short.parseShort(tag.getFirst(FieldKey.DISC_NO));
        }
        if (tag.hasField(FieldKey.DISC_TOTAL)) {
            discTotal = Short.parseShort(tag.getFirst(FieldKey.DISC_TOTAL));
        }
        artistName = tag.getFirst(FieldKey.ARTIST);
        final String albumArtistName = tag.getFirst(FieldKey.ALBUM_ARTIST);
        final String albumName = tag.getFirst(FieldKey.ALBUM);
        album = new Album(albumName, albumArtistName);
        releaseDate = tag.getFirst(FieldKey.YEAR); // Appears to be a UTC formatted date string - but not always!
        if (tag.hasField(FieldKey.ORIGINAL_YEAR)) {
            originalReleaseDate = tag.getFirst(FieldKey.ORIGINAL_YEAR);
        }
        genre = String.join(",", tag.getAll(FieldKey.GENRE));
        musicBrainzTrackId = tag.getFirst(FieldKey.MUSICBRAINZ_TRACK_ID);

        AudioHeader audioHeader = audioFile.getAudioHeader();
        length = audioHeader.getTrackLength();
        encodingType = audioHeader.getEncodingType();
        format = audioHeader.getFormat();
        bitRate = audioHeader.getBitRate();
        lossless = audioHeader.isLossless();
        variableBitRate = audioHeader.isVariableBitRate();

        try {
            // TODO - figure this part out. Is it possible to get Windows URI while running in *nix?
            //      - even if it's just an approximation?
            // What do we do in the case that we're running this in both Windows and *nix?
            File fileRef = audioFile.getFile();
            windowsFileUrl = fileRef.getCanonicalPath();
            posixFileUrl = fileRef.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return title + " by " + artistName;
    }
}
