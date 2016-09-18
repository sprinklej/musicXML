package musicXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.input.KeyEvent;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.beans.value.ChangeListener;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


public class Controller {
    Database db;
    MusicXMLFile crntSelection = null;

    @FXML
    private TextField searchField;
    @FXML
    private TableView<MusicXMLFile> tView;
    @FXML
    private TableColumn<MusicXMLFile, String> songTitleCol;
    @FXML
    private TableColumn<MusicXMLFile, String> composerCol;

    @FXML
    private TextArea txtArea;

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
    private void handleAddButton(ActionEvent event) {
        System.out.println("Add clicked");

        // TODO move to its own class/function
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mXMLDetails.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Song Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        System.out.println("Edit clicked");
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        System.out.println("Export clicked");
    }

    @FXML
    private void handleTableViewClick(MouseEvent click) {

        if (tView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("null");
            return;
        }



        MusicXMLFile test = tView.getSelectionModel().getSelectedItem();
        txtArea.setText("id: " + test.getId());
        txtArea.appendText("\nsongTitle: " + test.getSongTitle());
        txtArea.appendText("\ncomposer: " + test.getComposer());
        txtArea.appendText("\nfilePath: " + test.getFilePath());
        System.out.println(test.toString());
        /*if (click.getClickCount() == 2) {
            System.out.println("doubleClick");
        } else if (click.getClickCount() == 1) {
            System.out.println("singleClick");
        }*/

        //tView.getSelectionModel().clearSelection();
    }

    //fill tableView
    private void fillTableView() {
        // uses tableview
        songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        composerCol.setCellValueFactory(new PropertyValueFactory<>("composer"));
        tView.getItems().addAll(db.getMXMLList());
    }
}
