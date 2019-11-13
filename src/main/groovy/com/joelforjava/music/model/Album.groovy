package com.joelforjava.music.model

class Album {
    String title;
    List<Song> tracks;
    int numPieces; // Number of discs, LPs, etc.
    String albumArtist;
    String recordLabel; // TODO - make a list?
    String barcode;
    String catalogNumber;
    // At this point, just to keep things somewhat simple, we'll assume a song release
    // date will also be the album release date
    String releaseDate;
    String originalReleaseDate;
    String albumCoverKey; // Could be, for example, a UUID to a file in an S3 bucket or on local filesystem
    String releaseType;
    String musicBrainzReleaseId;

    // TODO - determine constructors
    Album(String title, String albumArtist) {
        this.title = title;
        this.albumArtist = albumArtist;
    }

    @Override
    String toString() {
        return title + " by " + albumArtist;
    }
    // TODO - equals and hashCode
}
