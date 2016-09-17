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


public class Controller {
    @FXML
    private Button importButton;
    @FXML
    private Button exportButton;
    @FXML
    private ListView<String> lView;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        // using listview
        Database db = new Database();
        db.getXMLList();
        ArrayList<MusicXMLFile> XMLList = db.getXMLList();
        ObservableList<String> items = FXCollections.observableArrayList ();
        for (MusicXMLFile mXMLFile : XMLList) {
            items.add(mXMLFile.toString());
        }
        lView.setItems(items);
    }
}
