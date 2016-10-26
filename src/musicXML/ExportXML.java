package musicXML;

import parsed.*;
import parser.XMLConsts;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sprinklej on 2016-10-25.
 */
public class ExportXML {
    private Score score;
    private File file;
    private PrintWriter writer;

    public ExportXML(Score aScore) {
        score = aScore;

        // create the new file
        createFile();
        // fill the header part of the file
        printHeader();
        // fill the body of the file
        printBody();
        // close the file
        writer.println("</" + score.getScoreType() + ">");
        writer.close();
    }

    //http://stackoverflow.com/questions/6142901/how-to-create-a-file-in-a-directory-in-java
    private void createFile() {
        file = new File("testFile.xml");

        try {
            //f.getParentFile().mkdirs();
            file.createNewFile();
            writer = new PrintWriter(file, "UTF-8");
        } catch (IOException e) {
            System.out.println("ERROR: Could Not create new XML file");
            e.printStackTrace();
        }
    }


    // ------------------------- MAIN EXPORTER FOR THE HEADER PART OF THE XML -------------------------
    private void printHeader() {
        // XML VERSION and DOCTYPE
        writer.println(score.getxmlVersDocType());

        // SCORE-TYPE
        writer.print("<" + score.getScoreType());
        if (score.getScoreVersion() != null) {
            writer.println(" " + XMLConsts.VERSION + "=\"" + score.getScoreVersion() + "\">");
        } else {
            writer.println(">");
        }

        // WORK - minOccurs=0
        if (score.getWork() != null) {
            printWork();
        }

        // MOVEMENT-NUMBER - minOccurs=0
        if (score.getMovementNumber() != null) {
            writer.println("  <movement-number>" + score.getMovementNumber() + "</movement-number>");
        }

        // MOVEMENT-TITLE - minOccurs=0
        if (score.getMovementTitle() != null) {
            writer.println("  <movement-title>" + score.getMovementTitle() + "</movement-title>");
        }

        // IDENTIFICATION - minOccurs=0
        if (score.getIdentification() != null) {
            printIdentification();
        }

        // DEFAULTS - minOccurs=0
        if (score.getDefaults() != null) {
            // TODO
        }

        // CREDIT - minOccurs=0 maxOccurs="unbounded"
        if (score.getCredit() != null) {
            // TODO
        }

        // PART-LIST - MUST OCCUR
        // TODO


    }

    // WORK
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void printWork() {
        writer.println("  <work>");
        // work-number - minOccurs=0
        if (score.getWork().getWorkNumber() != null) {
            writer.println("    <work-number>"+ score.getWork().getWorkNumber() + "</work-number>");
        }
        // work-title - minOccurs=0
        if (score.getWork().getWorkTitle() != null) {
            writer.println("    <work-title>" + score.getWork().getWorkTitle() + "</work-title>");
        }
        // opus - minOccurs=0
        if (score.getWork().getOpus() == true) {
            writer.print("    <opus " + score.getWork().getOpusAttributes().getXmlns() + " "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_HREF + "=\"" + score.getWork().getOpusAttributes().getHref() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_TYPE + "=\"" + score.getWork().getOpusAttributes().getType() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_SHOW + "=\"" + score.getWork().getOpusAttributes().getShow() + "\" "
                    + XMLConsts.XLINK + ":" + XMLConsts.XLINK_ACTUATE + "=\"" + score.getWork().getOpusAttributes().getActuate() + "\"");

            if (score.getWork().getOpusAttributes().getRole() != null) {
                writer.print(" " + XMLConsts.XLINK + ":" + XMLConsts.XLINK_ROLE + "=\"" + score.getWork().getOpusAttributes().getRole() + "\"");
            }

            if (score.getWork().getOpusAttributes().getTitle() != null) {
                writer.print(" " + XMLConsts.XLINK + ":" + XMLConsts.XLINK_TITLE + "=\"" + score.getWork().getOpusAttributes().getTitle() + "\"");
            }
            writer.println("/>");
        }
        writer.println("  </work>");
    }


    // IDENTIFICATION
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------
    private void printIdentification() {
        writer.println("    <identification>");
        // creator - minOccurs=0 maxOccurs="unbounded"
        if (score.getIdentification().getCreator() != null) {
            for (TypedText tt:score.getIdentification().getCreator()) {
                writer.println("      " + tt.toString());
                //writer.println("      <" + tt.getElementName() + " type=\"" + tt.getTypeText() + "\">"
                //        + tt.getText() + "</" + tt.getElementName() + ">");
            }
        }
        // rights - minOccurs=0 maxOccurs="unbounded"
        /*if (score.getIdentification().getRights() != null) {
            for (String s:score.getIdentification().getRights()) {
                writer.println("      <rights>" + s + "</rights>");
            }
        }*/
        if (score.getIdentification().getRights() != null) {
            for (TypedText tt:score.getIdentification().getRights()) {
                writer.println("      " + tt.toString());
            }
        }


        // encoding - minOccurs=0
        // TODO
        // source - minOccurs=0
        if (score.getIdentification().getSource() != null) {
            writer.println("      <source>" + score.getIdentification().getSource() + "</source>");
        }
        // relation - minOccurs=0 maxOccurs="unbounded"
        if (score.getIdentification().getRelation() != null) {
            for (String s:score.getIdentification().getRelation()) {
                writer.println("      <relation>" + s + "</relation>");
            }
        }
        // miscellaneous - minOccurs=0
        // TODO

/*
        // encoding-date
        if (score.getIdentification().geteDate() != null) {
            writer.println("      <encoding-date>" + score.getIdentification().geteDate() + "</encoding-date>");
        }
        // encoding-
*/
        writer.println("    </identification>");
    }


    // DEFAULTS
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------

    // CREDIT
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------

    // PART-LIST
    // ------------------------- SCORE-HEADER TOP-LEVEL ITEM -------------------------




    // BODY
    // ------------------------- MAIN EXPORTER FOR THE BODY PART OF THE XML -------------------------
    private void printBody() {
        // TODO
    }
}
