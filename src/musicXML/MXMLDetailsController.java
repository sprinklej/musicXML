/**
 * Created by sprinklej on 2016-09-18.
 */

package musicXML;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MXMLDetailsController {
    MusicXMLFile data;

    @FXML
    TextField songTitleField;

    @FXML
    TextField composerField;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;

    @FXML
    void initialize() {}

    @FXML
    private void handleOkButton(ActionEvent event) {
        System.out.println("clicked OK");

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        System.out.println("Cancel clicked");
        //http://stackoverflow.com/questions/25037724/how-to-close-a-java-window-with-a-button-click-javafx-project
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void passData(MusicXMLFile aData) {
        data = aData;

        // display the data
        if(data.getId() != -1) {
            songTitleField.setText(data.getSongTitle());
            composerField.setText(data.getComposer());
        }
    }

    private void setData() {

    }
}
