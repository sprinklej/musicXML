package musicXML;


import javafx.beans.property.StringProperty ;
import javafx.beans.property.SimpleStringProperty ;

/**
 * Created by sprinklej on 2016-09-16.
 */
public class MusicXMLFile {

    private Integer id;
    private String filePath;

    private StringProperty songTitle = new SimpleStringProperty(this, "songTitle");
    private StringProperty composer = new SimpleStringProperty(this, "composer");;


    public MusicXMLFile(Integer aid, String aSongTitle,  String aComposer, String aFilePath){
        id = aid;
        setSongTitle(aSongTitle);
        setComposer(aComposer);
        filePath = aFilePath;

        //songTitle = aSongTitle;
        //composer = aComposer;
    }

    //String Properties
    private final StringProperty songTitleProp = new SimpleStringProperty(this, "songTitleProp");
    public StringProperty songTitleProperty() {
        return songTitleProp;
    }

    private final StringProperty composerProp = new SimpleStringProperty(this, "composerProp");
    public StringProperty composerProperty() {
        return composerProp;
    }

    //getters
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

    //setters
    public void setSongTitle(String aSongTitle) {
        songTitleProperty().set(aSongTitle);
    }

    public void setComposer(String aComposer) {
        composerProperty().set(aComposer);
    }


    public String toString(){
        return "ID#: " + id + ", songTitle: " + songTitle + ", composer: " + composer + ", filePath: " + filePath;
    }
}
