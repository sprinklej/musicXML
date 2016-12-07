/*
 * The main Parser for MusicXML files
 */


package parser;

import java.io.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import musicXML.MusicXMLFile;
import parsed.Score;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

public class XMLParser {
    private MusicXMLFile currentSong = null;
    private XMLStreamReader2 xmlStreamReader;
    private Score score = null;

    private ParseXMLHeader parseHeaderObj;
    private ParseXMLBody   parseBodyObj;


    // constructor
    public XMLParser(MusicXMLFile aCurrentSong) {
        currentSong = aCurrentSong;
    }

    // GETTERS
    public Score getScore(){
        return score;
    }

    // Start Parsing
    public void startParsing() throws XMLStreamException {
        String xmlFileName = currentSong.getFilePath();

        InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory2.SUPPORT_DTD, false);   //do not read DTD - TODO read local copy
        //xmlInputFactory.setProperty(XMLInputFactory2.P_DTD_OVERRIDE,
        //SchemaFactory.newInstance("/Users/sprinklej/Downloads/MusicXML/musicxml30/musicxml.xsd"));



        try {
            xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlFileName, new FileInputStream(xmlFileName));
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.out.println("ERROR: File not found");
            return;
        }

        parseHeaderObj = new ParseXMLHeader(xmlStreamReader);
        parseBodyObj = new ParseXMLBody(xmlStreamReader);
        getElements(xmlStreamReader, () -> scoreStart(), () -> scoreEnd());


    }


    /*
     * How to pass methods as arguments in Java
     * http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
     * http://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
     * http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
     * http://stackoverflow.com/questions/4685435/what-is-the-cloest-thing-to-a-function-pointer-in-java
     */
    public static void getElements(XMLStreamReader2 xmlStreamReader, Interface startMethod, Interface endMethod) {
        try {
           wLoop: while(xmlStreamReader.hasNext()){
                int eventType = xmlStreamReader.next();
                switch (eventType) {
                    case XMLEvent.START_ELEMENT:
                        //System.out.print("<" + xmlStreamReader.getName().toString() + ">");
                        if (startMethod.interfaceMethod()) {
                            break wLoop;
                        }
                        break;
                    /* Parse characters
                    case XMLEvent.CHARACTERS:
                        //System.out.print(xmlStreamReader.getText());
                        charMethod.interfaceMethod();
                        break;
                    */
                    case XMLEvent.END_ELEMENT:
                        //System.out.println("</"+xmlStreamReader.getName().toString()+">");
                        if (endMethod.interfaceMethod()) {
                            break wLoop;
                        }
                        break;

                    default:
                        //do nothing
                        break;
                }
            }
        } catch (XMLStreamException e) {
            //e.printStackTrace();
            System.out.println("ERROR: Stream Exception - Streaming file error");
        }
    }


    // Starts parsing the score
    private Boolean scoreStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_PARTWISE)) {
            // create part-wise score object
            score = new Score(XMLConsts.PARTWISE);
            if (xmlStreamReader.getAttributeCount() == 1) { // only has one option attribute - version
                score.setScoreVersion(xmlStreamReader.getAttributeValue(0));
            }
            parseHeaderObj.setScore(score);
            parseBodyObj.setScore(score);
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.SCORE_TIMEWISE)) {
            // create time-wise score object
            score = new Score(XMLConsts.TIMEWISE);
            if (xmlStreamReader.getAttributeCount() == 1) { // only has one option attribute - version
                score.setScoreVersion(xmlStreamReader.getAttributeValue(0));
            }
            parseHeaderObj.setScore(score);
            parseBodyObj.setScore(score);
        }

        parseHeaderObj.parseHeader();
        parseBodyObj.parseBody();
        return false;
    }
    private Boolean scoreEnd() {
        return false;
    }

}