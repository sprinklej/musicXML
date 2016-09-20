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
import javafx.stage.WindowEvent;

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



public class Controller {
    Database db;
    MusicXMLFile currentSong = null;
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

        //http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        // listener for tableview
        tView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MusicXMLFile>() {
            @Override
            public void changed(ObservableValue<? extends MusicXMLFile> observable, MusicXMLFile oldValue, MusicXMLFile newValue) {
                if (newValue == null) {
                    currentSong = null;
                    return;
                }
                currentSong = newValue;

                txtArea.setText("id: " + currentSong.getId());
                txtArea.appendText("\nsongTitle: " + currentSong.getSongTitle());
                txtArea.appendText("\ncomposer: " + currentSong.getComposer());
                txtArea.appendText("\nfilePath: " + currentSong.getFilePath());
            }
        });
    }


    @FXML
    private void handleAddButton(ActionEvent event) {
        System.out.println("Add clicked");
        songDetailsWindow(db, new MusicXMLFile(-1,"-1","-1", "-1"));
    }

    @FXML
    private void handleEditButton(ActionEvent event) {
        System.out.println("Edit clicked");
        //nothing to edit
        if (tView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        songDetailsWindow(db, currentSong);
    }

    @FXML
    private void handleExportButton(ActionEvent event) {
        System.out.println("Export clicked");
    }

/*   @FXML
    private void handleTableViewClick(MouseEvent click) {
        if (tView.getSelectionModel().getSelectedItem() == null) {
            //System.out.println("Empty row");
            return;
        } else {
            currentSong = tView.getSelectionModel().getSelectedItem();
        }

        if(click.getButton().equals(MouseButton.PRIMARY) && click.getClickCount() == 1) {
            txtArea.setText("id: " + currentSong.getId());
            txtArea.appendText("\nsongTitle: " + currentSong.getSongTitle());
            txtArea.appendText("\ncomposer: " + currentSong.getComposer());
            txtArea.appendText("\nfilePath: " + currentSong.getFilePath());
        }

    } */

    // fill tableView
    private void fillTableView() {
        currentSong = null; // clear current song
        tView.getItems().clear(); // clear the table before filling
        songTitleCol.setCellValueFactory(new PropertyValueFactory<>("songTitle"));
        composerCol.setCellValueFactory(new PropertyValueFactory<>("composer"));
        tView.getItems().addAll(currentList);
    }

    // create song details window
    private void songDetailsWindow(Database db, MusicXMLFile mXMLFile) {
        try {
            FXMLLoader root = new FXMLLoader(getClass().getResource("mXMLDetails.fxml"));
            Stage stage = new Stage();



            //http://stackoverflow.com/questions/19953306/block-parent-stage-until-child-stage-closes
            // block/disable main window
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.primaryStage);
            stage.setTitle("Song Details");

            // callback for when the window closes
            //http://stackoverflow.com/questions/34590798/how-to-refresh-parent-window-after-closing-child-window-in-javafx
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    //System.out.println("REFRESH HERE");
                    currentList = db.getMXMLList();
                    Controller.this.fillTableView();

                }
            });

            stage.setScene(new Scene(root.load()));
            MXMLDetailsController controller = root.<MXMLDetailsController>getController();
            controller.passData(db, mXMLFile);
            stage.show();
        } catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

