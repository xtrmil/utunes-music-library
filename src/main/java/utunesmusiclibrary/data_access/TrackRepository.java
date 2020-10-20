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
                    conn.prepareStatement("SELECT Track.Name AS TN, Artist.Name AS AN, Album.Title AS ATL, Genre.Name AS GN FROM Track " +
                            "INNER JOIN Album " +
                            "ON Track.AlbumId = Album.AlbumId " +
                            "INNER JOIN Artist " +
                            "ON Album.ArtistId = Artist.ArtistId " +
                            "INNER JOIN Genre " +
                            "ON Track.GenreId = Genre.GenreId " +
                            "WHERE Track.Name LIKE ? ");
            prep.setString(1,"%"+searchTerm+"%"); // get information about track that contains searchterm anywhere in the name
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

    public String[][] getRandomItems() {
        ArrayList<String> tempArray = new ArrayList<>();
        String[][] items = new String[3][5];
        List<PreparedStatement> statements = new ArrayList<>(); // creates an array of prepared statements

        try{
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

            for (int i = 0; i < statements.size(); i++) { // run every statement in array and add all items to a new array
                ResultSet set = statements.get(i).executeQuery();
                while(set.next()) {
                    tempArray.add(set.getString("Name"));
                }
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
        int count = 0;
        for (int i = 0; i < items.length; i++) { // creating a two-dimensional containing the items with a structure of [3][5]
            for (int j = 0; j < items[i].length; j++) {
                items[i][j] = tempArray.get(j+count);
            }
            count = count + 5;
        }
        return items;
    }
}
