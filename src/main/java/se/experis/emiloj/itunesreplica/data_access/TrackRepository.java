package se.experis.emiloj.itunesreplica.data_access;

import se.experis.emiloj.itunesreplica.models.Customer;
import se.experis.emiloj.itunesreplica.models.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TrackRepository {

    private String URL ="jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public ArrayList<Track> getTracksByName(String searchTerm){
       ArrayList<Track> tracks = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Track.Name AS TN, Artist.Name AS AN, Album.Title AS ATL, Genre.Name AS GN FROM Track " +
                            "INNER JOIN Album " +
                            "ON Track.AlbumId = Album.AlbumId " +
                            "INNER JOIN Artist " +
                            "ON Album.ArtistId = Artist.ArtistId " +
                            "INNER JOIN Genre " +
                            "ON Track.GenreId = Genre.GenreId " +
                            "WHERE Track.Name LIKE ? ");
            prep.setString(1,"%"+searchTerm+"%");
            ResultSet set = prep.executeQuery();
            while(set.next()){
                tracks.add( new Track(
                    set.getString("TN"),
                    set.getString("AN"),
                    set.getString("ATL"),
                    set.getString("GN")
                ));
            }

        }catch(Exception exception){
            System.out.println(exception.toString());
        }
        finally {
            try{
                conn.close();
            } catch (Exception exception){
                System.out.println(exception.toString());
            }
        }
        return tracks;
    }
}
