package utunesmusiclibrary.data_access;

import utunesmusiclibrary.models.Track;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrackRepository {

    private String URL ="jdbc:sqlite::resource:Chinook_Sqlite.sqlite";
    private Connection conn = null;

    public ArrayList<Track> getTracksByName(String searchTerm){
        ArrayList<Track> tracks = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(URL);
            PreparedStatement prep =
                    conn.prepareStatement("SELECT Track.Name AS tName, Artist.Name AS aName, Album.Title AS aTitle, Genre.Name AS gName FROM Track " +
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
                    set.getString("tName"),
                    set.getString("aName"),
                    set.getString("aTitle"),
                    set.getString("gName")
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

    public String[][] getRandomItems() {
        ArrayList<String> tempArray = new ArrayList<>(); // array that will store response from sql queries
        String[][] items = new String[3][5];
        List<PreparedStatement> statements = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(URL);

            PreparedStatement prep1 =
                    conn.prepareStatement("SELECT Track.Name FROM Track ORDER BY RANDOM() LIMIT 5");
            PreparedStatement prep2 =
                    conn.prepareStatement("SELECT Artist.Name FROM Artist ORDER BY RANDOM() LIMIT 5");
            PreparedStatement prep3 =
                    conn.prepareStatement("SELECT Genre.Name FROM Genre ORDER BY RANDOM() LIMIT 5");
            statements.add(prep1);
            statements.add(prep2);
            statements.add(prep3);

            for (int i = 0; i < statements.size(); i++) { // run every statement in array and add all items to an array
                ResultSet set = statements.get(i).executeQuery();
                while(set.next()) {
                    tempArray.add(set.getString("Name"));
                }
            }
            int count = 0;
            for (int i = 0; i < items.length; i++) { // creating a two-dimensional array containing the items with a structure of [3][5]
                for (int j = 0; j < items[i].length; j++) {
                    items[i][j] = tempArray.get(j+count);
                }
                count = count + 5;
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
        return items;
    }
}
