/**
 * Created by sprinklej on 2016-09-16.
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
}
