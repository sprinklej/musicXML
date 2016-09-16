package musicXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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

        ObservableList<String> items = FXCollections.observableArrayList (
                "Song1", "Song2", "Song3", "Song4", "Song5", "Song6", "Song7", "Song8", "Song9", "Song10",
                "Song1", "Song2", "Song3", "Song4", "Song5", "Song6", "Song7", "Song8", "Song9", "Song10");
        lView.setItems(items);
    }
}
