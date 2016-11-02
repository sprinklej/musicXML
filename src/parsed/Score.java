package parsed;

import parsed.header.credit.Credit;
import parsed.header.defaults.Defaults;
import parsed.header.identification.Identification;
import parsed.header.work.Work;
import parser.PartListWrapper;
import parser.XMLConsts;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-02.
 * FROM XSD: The score-partwise element is the root element for a partwise MusicXML score. It includes a score-header
 * group followed by a series of parts with measures inside. The document-attributes attribute group includes
 * the version attribute.
 * FROM XSD: The score-timewise element is the root element for a timewise MusicXML score. It includes a score-header
 * group followed by a series of measures with parts inside. The document-attributes attribute group includes
 * the version attribute.
 */
public class Score {
    private String scoreType;                   // partwise or timewise
    private String scoreVersion;                // Optional - The musicxml version

    // old way - killing
    //private Defaults defaults;                  // minOccurs=0
    private ArrayList<Credit> credit;           // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<PartListWrapper> partList;// minOccurs=1 maxOccurs=1



    // new way
    private ComplexElement work;            // minOccurs=0
    private Element movementNumberElement;  // minOccurs=0
    private Element movementTitleElement;   // minOccurs=0
    private ComplexElement identification;  // minOccurs=0
    private ComplexElement defaults;        // minOccurs=0


    private ArrayList<ElementWrapper> body; // minOccurs=1 maxOccurs="Unbounded" - top element is part OR measure


    public Score(String aScoreType){
        scoreType = aScoreType;
        credit = new ArrayList<Credit>();
        partList = new ArrayList<PartListWrapper>();

        // new way
        body = new ArrayList<ElementWrapper>();
    }


    // GETTERS
    public String getScoreType() {
        return scoreType;
    }

    public String getScoreVersion() {
        return scoreVersion;
    }


    public ArrayList<Credit> getCredit() {
        return credit;
    }

    public ArrayList<PartListWrapper> getPartList() {
        return partList;
    }






    // SETTERS
    public void setScoreType(String aScoreType){
        if (aScoreType != XMLConsts.PARTWISE || aScoreType != XMLConsts.TIMEWISE) {
            System.out.println("ERROR: Could not change score type");
            return;
        } else {
            scoreType = aScoreType;
        }
    }

    public void setScoreVersion(String aVersion) {
        scoreVersion = aVersion;
    }

    // ADD TO ARRAYLIST
    public void addToCredit(Credit aCredit) {
        credit.add(aCredit);
    }

    public void addToPartList(PartListWrapper aPartListWrapper) {
        partList.add(aPartListWrapper);
    }







    // NEW WAY
    // GETTERS
    public ComplexElement getWork() {
        return work;
    }

    public Element getMovementNumberElement() {
        return movementNumberElement;
    }

    public Element getMovementTitleElement() {
        return movementTitleElement;
    }

    public ComplexElement getIdentification() {
        return identification;
    }

    public ComplexElement getDefaults() {
        return defaults;
    }

    public ArrayList<ElementWrapper> getBody() {
        return body;
    }


    // SETTERS
    public void setWork(ComplexElement e) {
        work = e;
    }

    public void setIdentification(ComplexElement e) {
        identification = e;
    }

    public void setDefaults(ComplexElement e) {
        defaults = e;
    }

    public void setMovementNumberElement(Element e) {
        movementNumberElement = e;
    }

    public void setMovementTitleElement(Element e) {
        movementTitleElement = e;
    }


    // ADD TO
    public void addToBody(ElementWrapper e) {
        body.add(e);
    }
}
