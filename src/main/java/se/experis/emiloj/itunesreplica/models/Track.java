package se.experis.emiloj.itunesreplica.models;

public class Track {
    private String trackName,artistName,albumName,genreName;

    public Track(){

    }

    public Track(String trackName, String artistName, String albumName, String genreName) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.genreName = genreName;
    }

    public String getTrackName() { return trackName; }
    public String getArtistName() { return artistName; }
    public String getAlbumName() { return albumName; }
    public String getGenreName() { return genreName; }

    public void setTrackName(String trackName) { this.trackName = trackName; }    // ta bort?
    public void setArtistName(String artistName) { this.artistName = artistName; }
    public void setAlbumName(String albumName) { this.albumName = albumName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }

}

