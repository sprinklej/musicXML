/**
 * Created by sprinklej on 2016-09-16.
 * http://stackoverflow.com/questions/18497699/populate-a-tableview-using-database-in-javafx
 * http://stackoverflow.com/questions/26980180/populating-a-tableview-with-data-from-database-javafx
 * http://stackoverflow.com/questions/25651641/javafx-mysql-connection-example-please
 */


package musicXML;

import java.sql.*;
import java.util.ArrayList;



public class Database {

    private Connection database;
    private Statement stat;

    public Database() {
        try {
            Class.forName("org.sqlite.JDBC");
            database = DriverManager.getConnection("jdbc:sqlite:database/musicXML.db");
            stat = database.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            if (database != null) {
                database.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MusicXMLFile> getMXMLList() {
        ArrayList<MusicXMLFile> XMLList = new ArrayList<MusicXMLFile>();
        String sqlQueryString = "SELECT * FROM musicXMLFiles;";

        try {
            ResultSet rs = stat.executeQuery(sqlQueryString);
            while (rs.next()) {
                /*System.out.println("id " + rs.getInt("id"));
                System.out.println("songTitle " + rs.getString("songTitle"));
                System.out.println("composer: " + rs.getString("composer"));
                System.out.println("filePath " + rs.getString("filePath"));*/
                MusicXMLFile MXMLfile = new MusicXMLFile(rs.getInt("id"), rs.getString("songTitle"),rs.getString("composer"), rs.getString("filePath"));
                XMLList.add(MXMLfile);
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return XMLList;
    }

    public ArrayList<MusicXMLFile> searchXMLList(String searchTxt) {
        ArrayList<MusicXMLFile> XMLList = new ArrayList<MusicXMLFile>();
        String sqlQueryString = "SELECT * FROM musicXMLFiles WHERE songTitle like '%" + searchTxt +
                "%' OR composer like '%" + searchTxt + "%';";

        try {
            ResultSet rs = stat.executeQuery(sqlQueryString);
            while (rs.next()) {
                /*System.out.println("id " + rs.getInt("id"));
                System.out.println("songTitle " + rs.getString("songTitle"));
                System.out.println("composer: " + rs.getString("composer"));
                System.out.println("filePath " + rs.getString("filePath"));*/
                MusicXMLFile MXMLfile = new MusicXMLFile(rs.getInt("id"), rs.getString("songTitle"), rs.getString("composer"), rs.getString("filePath"));
                XMLList.add(MXMLfile);
            }
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return XMLList;
    }

    public void addSong(MusicXMLFile newSong) {
        // using prepared statements
        try {
            database.setAutoCommit(false);
            String sqlString = "INSERT INTO musicXMLFiles(songTitle, composer, filePath) VALUES (?, ?, ?);";
            PreparedStatement prep = database.prepareStatement(sqlString);
            prep.setString(1, newSong.getSongTitle());
            prep.setString(2, newSong.getComposer());
            prep.setString(3, newSong.getFilePath());

            prep.executeUpdate();
            database.commit();
            database.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Failed to update the database :(");
            // TODO Auto-generated catch block
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

    public void updateSong(MusicXMLFile currentSong) {

        String sqlQueryString = "UPDATE musicXMLFiles SET songTitle = '" +
                currentSong.getSongTitle().replaceAll("'", "''") + "', composer = '" +
                currentSong.getComposer().replaceAll("'", "''") + "', filePath = '" +
                currentSong.getFilePath().replaceAll("'", "''") + "' WHERE id = " + currentSong.getId() + ";";

        System.out.println(sqlQueryString);
        try {
            stat.executeUpdate(sqlQueryString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void deleteSong(MusicXMLFile currentSong) {
        String sqlQueryString = "DELETE FROM musicXMLFiles WHERE id = " + currentSong.getId() + ";";

        try {
            stat.executeUpdate(sqlQueryString);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
