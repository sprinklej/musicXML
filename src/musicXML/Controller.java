package musicXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.collections.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import javafx.beans.value.ChangeListener;

import javax.swing.plaf.basic.BasicOptionPaneUI;


public class Controller {
    Database db;

    @FXML
    private TextField searchField;
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
        db = new Database();

        // tableview
        assert tView != null : "fx:id=\"tView\" was not injected: check FXML file.";
        fillTableView();

        //http://code.makery.ch/blog/javafx-2-event-handlers-and-change-listeners/
        //listener for search field
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("searchField changed - newValue: " + newValue);
            }
        });
    }


    @FXML
    private void handleImportButton (ActionEvent event) {
        System.out.println("Import clicked");
        //outputTextArea.appendText("Button Action\n");
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        System.out.println("Export clicked");
    }

    //fill tableView
    private void fillTableView() {
        // uses tableview
        songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        composerCol.setCellValueFactory(new PropertyValueFactory<>("composer"));
        tView.getItems().addAll(db.getMXMLList());

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
