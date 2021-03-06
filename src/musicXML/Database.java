/*
 * Does all the database control for the application:
 * Add/remove/edit/display database entries
 *
 * http://stackoverflow.com/questions/18497699/populate-a-tableview-using-database-in-javafx
 * http://stackoverflow.com/questions/26980180/populating-a-tableview-with-data-from-database-javafx
 * http://stackoverflow.com/questions/25651641/javafx-mysql-connection-example-please
 */


package musicXML;

import java.sql.*;
import java.util.ArrayList;
import java.io.File;


public class Database {

    private Connection database;
    private Statement stat;

    // Constructor
    public Database() {
        try {
            //System.out.println(getDBFilePath());
            Class.forName("org.sqlite.JDBC");
            database = DriverManager.getConnection("jdbc:sqlite:" + "database" + File.separator + "musicXML.db");
            stat = database.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("FATAL ERROR: Could not find database class");
            System.exit(1);
            //e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("FATAL SQL/DATABASE ERROR: Could not load database");
            System.exit(1);
            //e.printStackTrace();
        }
    }


    // Close the DB
    public void closeDB() {
        try {
            if (database != null) {
                database.close();
            }
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR: Could not close the database");
            //e.printStackTrace();
        }
    }

    // get the MusicXML list from the DB
    public ArrayList<MusicXMLFile> getMXMLList() {
        ArrayList<MusicXMLFile> XMLList = new ArrayList<MusicXMLFile>();
        //String sqlQueryString = "SELECT * FROM musicXMLFiles;";
        String sqlQueryString = "SELECT * FROM musicXMLFiles ORDER BY songTitle asc;";

        try {
            ResultSet rs = stat.executeQuery(sqlQueryString);
            while (rs.next()) {
                MusicXMLFile mXMLFile = new MusicXMLFile(rs.getInt("id"), rs.getString("songTitle"),
                        rs.getString("composer"), rs.getString("filePath"));
                XMLList.add(mXMLFile);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL ERROR: Could not return data from the database");
            //e.printStackTrace();
        }

        return XMLList;
    }

    // Search the DB for some text string
    public ArrayList<MusicXMLFile> searchXMLList(String searchTxt) {
        ArrayList<MusicXMLFile> XMLList = new ArrayList<MusicXMLFile>();
        //String sqlQueryString = "SELECT * FROM musicXMLFiles WHERE songTitle like '%" + searchTxt +
        //        "%' OR composer like '%" + searchTxt + "%';";
        String sqlQueryString = "SELECT * FROM musicXMLFiles WHERE songTitle like '%" + searchTxt +
                "%' OR composer like '%" + searchTxt + "%' ORDER BY songTitle asc;";

        try {
            ResultSet rs = stat.executeQuery(sqlQueryString);
            while (rs.next()) {
                MusicXMLFile mXMLFile = new MusicXMLFile(rs.getInt("id"), rs.getString("songTitle"),
                        rs.getString("composer"), rs.getString("filePath"));
                XMLList.add(mXMLFile);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("SQL ERROR: Could not search the database");
            //e.printStackTrace();
        }

        return XMLList;
    }

    // Add a song to the DB
    public void addSong(MusicXMLFile newSong) {
        // using prepared statements
        try {
            database.setAutoCommit(false);
            String sqlString = "INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES (?, ?, ?);";
            PreparedStatement prep = database.prepareStatement(sqlString);
            prep.setString(1, newSong.getSongTitle()); // handles .replaceAll("'", "''")) for you
            prep.setString(2, newSong.getComposer());
            prep.setString(3, newSong.getFilePath());

            prep.executeUpdate();
            database.commit();
            database.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to ADD to the database");
            //e.printStackTrace();
        }

        /*
        // Not using prepared statements
        String sqlString = "INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES ('"+
                newSong.getSongTitle().replaceAll("'", "''") + "', '" + newSong.getComposer().replaceAll("'", "''") +
                "', '" + newSong.getFilePath().replaceAll("'", "''") + "');";
        System.out.println(sqlString);
        try {
            stat.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    // Update a song in the DB
    public void updateSong(MusicXMLFile currentSong) {
        // using prepared statements
        try {
            database.setAutoCommit(false);
            String sqlString = "UPDATE musicXMLFiles SET songTitle = ?, composer = ?, filePath = ? WHERE id = " +
            currentSong.getId() + ";";
            PreparedStatement prep = database.prepareStatement(sqlString);
            prep.setString(1, currentSong.getSongTitle());
            prep.setString(2, currentSong.getComposer());
            prep.setString(3, currentSong.getFilePath());

            prep.executeUpdate();
            database.commit();
            database.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("SQL ERROR: Failed to update the database");
            //e.printStackTrace();
        }

        /*
        // Not using prepared statements
        String sqlString = "UPDATE musicXMLFiles SET songTitle = '" +
                currentSong.getSongTitle().replaceAll("'", "''") + "', composer = '" +
                currentSong.getComposer().replaceAll("'", "''") + "', filePath = '" +
                currentSong.getFilePath().replaceAll("'", "''") + "' WHERE id = " + currentSong.getId() + ";";

        System.out.println(sqlString);
        try {
            stat.executeUpdate(sqlString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    // Delete a song from the DB
    public void deleteSong(MusicXMLFile currentSong) {
        String sqlQueryString = "DELETE FROM musicXMLFiles WHERE id = " + currentSong.getId() + ";";

        try {
            stat.executeUpdate(sqlQueryString);
        } catch (SQLException e) {
            System.out.println("SQL ERROR: Failed to DELETE from the database");
            //e.printStackTrace();
        }
    }
}
