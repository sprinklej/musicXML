/*
 * The Controller for the main window
 */

package musicXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ChangeListener;
import parser.XMLParser;

import javax.xml.stream.XMLStreamException;


public class Controller {
    private Database db;
    private XMLParser parser;
    private ExportXML exporter;
    private MusicXMLFile currentSong = null;
    private ArrayList<MusicXMLFile> currentList = null;



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
    @FXML
    Button exportButton;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // load database
        db = new Database();
        currentList = db.getMXMLList();
        fillTableView();

        // listener for search field
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() == 0) {
                    //System.out.println("not searching for anything - repopulate the table");
                    currentList = db.getMXMLList();
                    fillTableView();
                }

                if(newValue.length() > 0) {
                    //System.out.println("search for: " + newValue);
                    currentList = db.searchXMLList(newValue);
                    fillTableView();
                }
            }
        });

        // Create a listener for a tableview
        // http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview
        tView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MusicXMLFile>() {
            @Override
            public void changed(ObservableValue<? extends MusicXMLFile> observable, MusicXMLFile oldValue, MusicXMLFile newValue) {
                if (newValue == null) {
                    currentSong = null;
                    return;
                }
                currentSong = newValue;

                txtArea.setText("Song Info:\n\n");
                txtArea.appendText("ID: " + currentSong.getId() + "\n");
                //txtArea.appendText("\nsongTitle: " + currentSong.getSongTitle());
                //txtArea.appendText("\ncomposer: " + currentSong.getComposer());
                txtArea.appendText("FilePath: " + currentSong.getFilePath() + "\n\n");

                parser = new XMLParser(currentSong);
                try {
                    parser.startParsing();
                } catch (XMLStreamException e) {
                    txtArea.appendText("Error: Unable to parse file!\n");
                    return;
                    //e.printStackTrace();
                }

                // Check if score is valid
                if (parser.getScore() == null) {
                    txtArea.appendText("Error: Unable to parse file!\n");
                    return;
                }

                // get some parsed info an display it
                GetParsedData data = new GetParsedData(parser.getScore());
                txtArea.appendText("****Score Parsed****\n");
                txtArea.appendText(data.getScoreType());
                txtArea.appendText(data.getWorkInfo());
                txtArea.appendText(data.getMovementNumber());
                txtArea.appendText(data.getMovementTitle());
                txtArea.appendText(data.getIdentification());
                txtArea.appendText("\nPart info:\n");
                txtArea.appendText(data.getPartInfo());
            }
        });
    }


    @FXML
    private void handleAddButton(ActionEvent event) {
        System.out.println("Add clicked");
        songDetailsWindow(db, new MusicXMLFile(-1,"-1","-1","-1"));
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
        if (tView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        File file = showSaveDialog();
        if (file != null) {
            exporter = new ExportXML(file, parser.getScore());
            Boolean success = exporter.writeFile();

            if (success) {
                txtArea.appendText("\n**Export Complete.**\n");
            } else {
                txtArea.appendText("\nError: Export Failed!\n");
            }
        }
    }
    private File showSaveDialog() {
        File file = new File(currentSong.getFilePath());

        Stage stage = (Stage) exportButton.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export musicXML");
        fileChooser.setInitialFileName(file.getName());
        file = fileChooser.showSaveDialog(stage);
        return file;
    }



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

            // block/disable main window
            // http://stackoverflow.com/questions/19953306/block-parent-stage-until-child-stage-closes
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Main.primaryStage);
            stage.setTitle("Song Details");

            // callback for when the window closes
            // http://stackoverflow.com/questions/34590798/how-to-refresh-parent-window-after-closing-child-window-in-javafx
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    currentList = db.getMXMLList();
                    fillTableView();

                }
            });

            stage.setScene(new Scene(root.load()));
            //http://stackoverflow.com/questions/14187963/passing-parameters-javafx-fxml
            MXMLDetailsController controller = root.<MXMLDetailsController>getController();
            controller.passData(db, mXMLFile);
            stage.show();
        } catch(Exception e) {
            txtArea.setText("ERROR: Failed to load song details window");
            System.out.println("ERROR: Failed to load song details window");
            //e.printStackTrace();
        }
    }
}

