/**
 * Created by sprinklej on 2016-09-18.
 */

package musicXML;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;

public class MXMLDetailsController {
    private Database db;
    private MusicXMLFile currentSong;

    @FXML
    TextField songTitleField;

    @FXML
    TextField composerField;

    @FXML
    TextField filePathField;

    @FXML
    Text warningText;

    @FXML
    Button confirmButton;

    @FXML
    Button deleteButton;

    @FXML
    Button cancelButton;

    @FXML
    void initialize() {
    }

    // pass data to the controller
    public void passData(Database adb, MusicXMLFile aData) {
        db = adb;
        currentSong = aData;
        setWindowDetails(); // setup details of the window
    }

    private void setWindowDetails() {
        // display the data - not a new song
        if(currentSong.getId() != -1) {
            confirmButton.setText("Update");
            songTitleField.setText(currentSong.getSongTitle());
            composerField.setText(currentSong.getComposer());
            filePathField.setText(currentSong.getFilePath());
        } else { // new song
            deleteButton.setDisable(true);
            deleteButton.setVisible(false);
            confirmButton.setText("Add");
        }
    }

    @FXML
    private void handleSelectFileButton(ActionEvent event) {
        System.out.println("Select File Clicked");
        // create the filechooser
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        // set extension filter - only shows *.xml, others can be added such as *.mxl (compressed xml files)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // display the filechooser
        fileChooser.setTitle("Select XML File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) { // display the filepath if the user didnt hit cancel
            filePathField.setText(file.getPath());
        }
    }

    @FXML
    private void handleConfirmButton(ActionEvent event) {
        System.out.println("Confirm Clicked");
        warningText.setText("");

        // check if all required fields filled in
        if(!checkFields()) {
            System.out.println("Empty text field");
            warningText.setText("Field(s) Required");
            return;
        }

        // get tect from text fields
        currentSong.setSongTitle(songTitleField.getText());
        currentSong.setComposer(composerField.getText());
        currentSong.setFilePath(filePathField.getText());

        // add song to DB or update song in DB
        if(currentSong.getId() == -1) {
            db.addSong(currentSong);
        } else {
            db.updateSong(currentSong);
        }

        //stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN));
        // close the window
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDeleteButton(ActionEvent event) {
        System.out.println("Delete clicked");
        db.deleteSong(currentSong);

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        System.out.println("Cancel clicked");
        //http://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean checkFields() {
        if(songTitleField.getText().equals("") || filePathField.getText().equals("")) {
            return false;
        }
        return true;
    }

}
