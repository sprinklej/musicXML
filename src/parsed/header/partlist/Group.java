package parsed.header.partlist;

import parsed.Attribute;
import parsed.Element;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class Group {
    private ArrayList<Attribute> groupAttributes;   // not required

    // GROUP-NAME
    private Element groupName;          // minOccurs=0

    // GROUP-NAME-DISPLAY
    private boolean groupNameDisplay;                   // minOccurs=0
    private Attribute groupNameDispAttribute;           // not required
    private ArrayList<Element> accidentalORDisplayText; // unbounded choice between the 2 basic elements so just create a list of them

    // GROUP-ABBREVIATION
    private Element groupAbbreviation;  // minOccurs=0

    // GROUP-ABBREVIATION-DISPLAY
    private boolean groupAbbreviationDisplay;           // minOccurs=0
    private Attribute groupAbbrevDispAttribute;         // not required
    private ArrayList<Element> groupAbbrevDispElements; // unbounded choice between the 2 basic elements so just create a list of them

    // GROUP-SYMBOL
    private Element groupSymbol;  // minOccurs=0

    // GROUP-BARLINE
    private Element groupBarline; // minOccurs=0

    // GROUP-TIME
    private Element groupTime;    // minOccurs=0

    // FOOTNOTE
    private Element footnote;      // minOccurs=0
    // LEVEL
    private Element level;         // minOccurs=0




    // CONSTRUCTOR
    public Group() {
        groupAttributes = new ArrayList<Attribute>();
        accidentalORDisplayText = new ArrayList<Element>();
        groupAbbrevDispElements = new ArrayList<Element>();
    }



    // GETTERS
    public ArrayList<Attribute> getGroupAttributes() {
        return groupAttributes;
    }

    public Element getGroupName() {
        return groupName;
    }

    public boolean getGroupNameDisplay() {
        return groupNameDisplay;
    }

    public Attribute getGroupNameDispAttribute() {
        return groupNameDispAttribute;
    }

    public ArrayList<Element> getAccidentalORDisplayText() {
        return accidentalORDisplayText;
    }

    public Element getGroupAbbreviation() {
        return groupAbbreviation;
    }

    public boolean getGroupAbbreviationDisplay() {
        return groupAbbreviationDisplay;
    }

    public Attribute getGroupAbbrevDispAttribute() {
        return groupAbbrevDispAttribute;
    }

    public ArrayList<Element> getGroupAbbrevDispElements() {
        return groupAbbrevDispElements;
    }

    public Element getGroupSymbol() {
        return groupSymbol;
    }

    public Element getGroupBarline() {
        return groupBarline;
    }

    public Element getGroupTime() {
        return groupTime;
    }

    public Element getFootnote() {
        return footnote;
    }

    public Element getLevel() {
        return level;
    }



    // SETTERS
    public void setGroupName(Element aGroupName) {
        groupName = aGroupName;
    }

    public void setGroupNameDisplay(boolean grpNamDisp) {
        groupNameDisplay = grpNamDisp;
    }

    public void setGroupNameDispAttribute(Attribute a) {
        groupNameDispAttribute = a;
    }

    public void setGroupAbbreviation(Element e) {
        groupAbbreviation = e;
    }

    public void setGroupAbbreviationDisplay(boolean GAD) {
        groupAbbreviationDisplay = GAD;
    }

    public void setGroupAbbrevDispAttribute(Attribute a) {
        groupAbbrevDispAttribute = a;
    }

    public void setGroupSymbol(Element e) {
        groupSymbol = e;
    }

    public void setGroupBarline(Element e) {
        groupBarline = e;
    }

    public void setGroupTime(Element e) {
        groupTime = e;
    }

    public void setFootnote(Element e) {
        footnote = e;
    }

    public void setLevel(Element e) {
        level = e;
    }


    // ADD TO
    public void addToGroupAttributes(Attribute a) {
        groupAttributes.add(a);
    }

    public void addToAcdntlORDsplyText(Element e) {
        accidentalORDisplayText.add(e);
    }

    public void addToGroupAbbrevElements(Element e) {
        groupAbbrevDispElements.add(e);
    }
}
