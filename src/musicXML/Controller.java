package musicXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import javafx.collections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private TableView<MusicXMLFile> tView;
    @FXML
    private TableColumn<MusicXMLFile, String> songTitleCol;
    @FXML
    private TableColumn<MusicXMLFile, String> composerCol;

    //@FXML
    //private ListView<String> lView;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // load database
        Database db = new Database();

        // uses tableview
        songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        composerCol.setCellValueFactory(new PropertyValueFactory<>("composer"));
        tView.getItems().addAll(db.getXMLList());

        /* //uses listview
        ArrayList<MusicXMLFile> XMLList = db.getXMLList();
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (MusicXMLFile mXMLFile : XMLList) {
            items.add(mXMLFile.toString());
        }
        lView.setItems(items);
        */
    }
}
