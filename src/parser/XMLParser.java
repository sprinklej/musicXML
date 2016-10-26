package parser;

import java.io.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import musicXML.MusicXMLFile;
import parsed.Score;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;


//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685563/how-to-pass-a-function-as-a-parameter-in-java
//http://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
//http://stackoverflow.com/questions/4685435/what-is-the-cloest-thing-to-a-function-pointer-in-java

/*
@FunctionalInterface
interface Interface {
    //Can only have 1 abstract method - therefore they must all return true/false
    //http://stackoverflow.com/questions/23682243/lambda-can-only-be-used-with-functional-interface
    public boolean interfaceMethod();
}
*/

/**
 * Created by sprinklej on 2016-09-23.
 */
public class XMLParser {
    private String xmlVersDocType;

    private MusicXMLFile currentSong = null;
    private Score currentScore = null;
    private XMLStreamReader2 xmlStreamReader = null;
    private Score score = null;

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

        // XML parser works weird to get doctype info
        //http://stackoverflow.com/questions/24666805/java-only-read-first-line-of-a-file
        //http://stackoverflow.com/questions/2312756/how-to-read-a-specific-line-using-the-specific-line-number-from-a-file-in-java
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(new FileReader(xmlFileName));
            xmlVersDocType = bReader.readLine();
            xmlVersDocType += "\n" + bReader.readLine();
            bReader.close();
            //System.out.println(text);
        //} catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        InputStream xmlInputStream = getClass().getResourceAsStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        xmlInputFactory.setProperty(XMLInputFactory2.SUPPORT_DTD, false);   //do not read DTD - TODO read local copy
        //xmlInputFactory.setProperty(XMLInputFactory2.P_DTD_OVERRIDE,
        //SchemaFactory.newInstance("/Users/sprinklej/Downloads/MusicXML/musicxml30/musicxml.xsd"));



        try {
            xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlFileName, new FileInputStream(xmlFileName));
            //System.out.println(xmlStreamReader.getVersion());
            //System.out.println(xmlStreamReader.getEncoding());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not found");
            return;
        }

        getElements(xmlStreamReader, () -> scoreStart(), () -> scoreEnd());

        // *****TEMP******
        System.out.println(score.toString());
        /*java.util.ArrayList<PartListWrapper> list = score.getPartList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIsPart() == true) {
                System.out.println(list.get(i).getPart().toString());
            } else {
                System.out.println(list.get(i).getGroup().toString());
            }
        }*/
    }


    //http://stackoverflow.com/questions/2671496/java-when-to-use-static-methods
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
/*
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
            e.printStackTrace();
            System.out.println("ERROR: Stream Exception - Streaming file error");
        }
    }


    //
    private Boolean scoreStart() {
        if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.PARTWISE)) {
            // create part-wise score object
            score = new Score(xmlVersDocType, XMLConsts.PARTWISE);
        } else if (xmlStreamReader.getName().toString().contentEquals(XMLConsts.TIMEWISE)) {
            // create time-wise score object
            score = new Score(xmlVersDocType, XMLConsts.TIMEWISE);
        }

        // parse xml header info
        parseHeader();

        // parse xml body info
        //parseBody();

        return false;
    }
    private Boolean scoreEnd() {
        return false;
    }




    // Parse the header of the score - all elements are common between partwise and timewise scores
    private void parseHeader() {
        ParseXMLHeader parseHeaderObj = new ParseXMLHeader(xmlStreamReader, score);
        parseHeaderObj.parseHeader();
    }

    // Parse the body of the score
    private void parseBody() {
        ParseXMLBody parseBodyObj = new ParseXMLBody(xmlStreamReader, score);
        if (score.getScoreType().contentEquals(XMLConsts.PARTWISE)) {
            parseBodyObj.partwiseBody();
        } else if (score.getScoreType().contentEquals(XMLConsts.TIMEWISE)) {
            parseBodyObj.timewiseBody();
        }
    }
}