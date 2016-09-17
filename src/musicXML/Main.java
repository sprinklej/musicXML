package musicXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.sql.*;
//import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*
        // create array of database results
        ArrayList<MusicXMLFile> songList = new ArrayList<MusicXMLFile>();

        //Connect to database
        try {
            Class.forName("org.sqlite.JDBC");
            Connection database = DriverManager.getConnection("jdbc:sqlite:database/musicXML.db");
            Statement stat = database.createStatement();

            String sqlQueryString = "select * from musicXMLFiles;";
            System.out.println("");
            System.out.println(sqlQueryString);

            ResultSet rs = stat.executeQuery(sqlQueryString);

            while (rs.next()) {
                System.out.println("songTitle " + rs.getString("songTitle"));
                System.out.println("composer: " + rs.getString("composer"));
                System.out.println("filePath " + rs.getString("filePath"));
                MusicXMLFile mXMLfile = new MusicXMLFile(rs.getString("songTitle"),rs.getString("composer"), rs.getString("filePath"));
                songList.add(mXMLfile);
            }
            rs.close();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // see array list
        for (MusicXMLFile song : songList) {
            String s = song.toString();
            System.out.println(s);
        }
        */


        Parent root = FXMLLoader.load(getClass().getResource("musicXML.fxml"));
        primaryStage.setTitle("musicXML Viewer");
        //primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
