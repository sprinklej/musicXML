/*
 * A MusicXML file - What shows up in the TableView
 */

package musicXML;

import javafx.beans.property.StringProperty ;
import javafx.beans.property.SimpleStringProperty ;


public class MusicXMLFile {

    private Integer id;
    private String filePath;
    private StringProperty songTitleProp = new SimpleStringProperty(this, "songTitleProp");
    private StringProperty composerProp = new SimpleStringProperty(this, "composerProp");

    public MusicXMLFile(Integer aid, String aSongTitle,  String aComposer, String aFilePath){
        id = aid;
        setSongTitle(aSongTitle);
        setComposer(aComposer);
        filePath = aFilePath;
    }

    // string Properties
    public StringProperty songTitleProperty() {
        return songTitleProp;
    }

    public StringProperty composerProperty() {
        return composerProp;
    }

    // getters
    public Integer getId() {
        return id;
    }

    public String getSongTitle() {
        return songTitleProperty().get();
    }

    public String getComposer() {
        return composerProperty().get();
    }

    public String getFilePath() {
        return filePath;
    }

    // setters
    public void setSongTitle(String aSongTitle) {
        songTitleProperty().set(aSongTitle);
    }

    public void setComposer(String aComposer) {
        composerProperty().set(aComposer);
    }

    public void setFilePath(String aFilePath) {
        filePath = aFilePath;
    }

    // toString
    public String toString(){
        return "ID#: " + id + ", songTitle: " + getSongTitle() + ", composer: " + getComposer() + ", filePath: " + filePath;
    }
}
