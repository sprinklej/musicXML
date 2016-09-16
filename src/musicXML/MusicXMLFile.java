package musicXML;

/**
 * Created by sprinklej on 2016-09-16.
 */
public class MusicXMLFile {

    private String songTitle;
    private String composer;
    private String filePath;

    public MusicXMLFile(String aSongTitle,  String aComposer, String aFilePath){
        songTitle = aSongTitle;
        composer = aComposer;
        filePath = aFilePath;
    }

    public String toString(){
        return "songTitle: " + songTitle + ", composer: " + filePath + ", filePath: " + filePath;
    }
}
