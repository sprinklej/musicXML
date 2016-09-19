/**
 * Created by sprinklej on 2016-09-18.
 */

package musicXML;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MXMLDetailsController {
    Database db;
    MusicXMLFile currentSong;

    @FXML
    TextField songTitleField;

    @FXML
    TextField composerField;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;

    @FXML
    void initialize() {
    }

    @FXML
    private void handleOkButton(ActionEvent event) {
        System.out.println("clicked OK");

        currentSong.setSongTitle(songTitleField.getText());
        currentSong.setComposer(composerField.getText());
        //currentSong.setFilePath(_____);

        if(currentSong.getId() == -1) {
            db.addSong(currentSong);
        } else {
            db.updateSong(currentSong);
        }


        Stage stage = (Stage) okButton.getScene().getWindow();

        //stage.getOnHidden().handle(new WindowEvent(stage, WindowEvent.WINDOW_HIDDEN));
        stage.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        System.out.println("Cancel clicked");
        //http://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void passData(Database adb, MusicXMLFile aData) {
        db = adb;
        currentSong = aData;

        // display the data - not a new song
        if(currentSong.getId() != -1) {
            songTitleField.setText(currentSong.getSongTitle());
            composerField.setText(currentSong.getComposer());
        }
    }
}
