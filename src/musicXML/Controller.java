package musicXML;

import com.sun.tools.javac.util.Name;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.net.URL;
import java.util.ArrayList;
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
import javax.swing.table.*;


public class Controller {
    Database db;
    ArrayList<MusicXMLFile> currentList = null;



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
        currentList = db.getMXMLList();
        fillTableView();

        //http://code.makery.ch/blog/javafx-2-event-handlers-and-change-listeners/
        //listener for search field
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {


                if(newValue.length() == 0) {
                    System.out.println("not searching for anything - repopulate the table");
                    currentList = db.getMXMLList();
                    fillTableView();
                }

                if(newValue.length() > 0) {
                    System.out.println("search for: " + newValue);
                    currentList = db.searchXMLList(newValue);
                    fillTableView();
                }
            }
        });
    }


    @FXML
    private void handleAddButton(ActionEvent event) {
        System.out.println("Add clicked");
        songDetailsWindow(new MusicXMLFile(-1,"-1","-1","-1"));
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        System.out.println("Edit clicked");
        //nothing to edit
        if (tView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        songDetailsWindow(tView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        System.out.println("Export clicked");
    }

    @FXML
    private void handleTableViewClick(MouseEvent click) {

        if (tView.getSelectionModel().getSelectedItem() == null) {
            //System.out.println("Empty row");
            return;
        }

        if(click.getButton().equals(MouseButton.PRIMARY) && click.getClickCount() == 1) {
            //System.out.println("primary mouse button clicked");
            //System.out.println(click.getTarget().toString());

            MusicXMLFile file = tView.getSelectionModel().getSelectedItem();
            txtArea.setText("id: " + file.getId());
            txtArea.appendText("\nsongTitle: " + file.getSongTitle());
            txtArea.appendText("\ncomposer: " + file.getComposer());
            txtArea.appendText("\nfilePath: " + file.getFilePath());
        }

        //tView.getSelectionModel().clearSelection();
        //tView.getSelectionModel().select(null);
    }

    // fill tableView
    private void fillTableView() {
        tView.getItems().clear(); // clear the table before filling
        songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        composerCol.setCellValueFactory(new PropertyValueFactory<>("composer"));
        tView.getItems().addAll(currentList);
    }

    // create song details window
    private void songDetailsWindow(MusicXMLFile mXMLFile) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("mXMLDetails.fxml"));
            Stage stage = new Stage();
            //http://stackoverflow.com/questions/19953306/block-parent-stage-until-child-stage-closes
            // block/disable main window
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.primaryStage);
            stage.setTitle("Song Details");
            stage.setScene(new Scene(root.load()));
            MXMLDetailsController controller = root.<MXMLDetailsController>getController();
            controller.passData(mXMLFile);
            stage.show();
        } catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

