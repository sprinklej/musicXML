package musicXML;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;

//import java.sql.*;
//import java.util.ArrayList;


public class Main extends Application {

    public static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

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
