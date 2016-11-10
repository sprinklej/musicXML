package parsed.header;

import parsed.Attribute;
import parsed.Element;
import parsed.header.Measure;
import parsed.header.identification.Identification;
import parsed.header.partlist.MidiInstrument;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class Part {
    private Attribute partIDAttribute; // required

    // IDENTIFICATION - same as the top level identification
    private Identification identification;     // minOccurs=0

    // PART-NAME
    private Element partName; // minOccurs=1 maxOccurs=1

    // PART-NAME-DISPLAY
    private boolean partNameDisplay; // minOccurs=0 - SUBTREE
    private Attribute partNameDispAttribute;
    private ArrayList<Element> partNameDispElements;

    // PART-ABBREVIATION
    private Element partAbbreviation; // minOccurs=0

    // PART-ABBREVIATION-DISPLAY
    private boolean partAbbreviationDisplay; // minOccurs=0 - SUBTREE
    private Attribute partAbbrevDispAttribute;
    private ArrayList<Element> partAbbrevDispElements;

    // GROUP
    private ArrayList<Element> group;   //minOccurs=0 maxOccurs="unbounded"

    // SCORE-INSTRUMENT
    private boolean scoreInstrument;    // minOccurs=0 maxOccurs="unbounded" - SUBTREE
    private Attribute scoreInstAttribute;
    private ArrayList<Element> scoreInstElements;

    // VIRTUAL-INSTRUMENT
    private boolean virtualInstrument;  // minOccurs=0 - SUBTREE of SCORE-INSTRUMENT
    private ArrayList<Element> virtualInstElements;

    // MIDI-DEVICE - unbounded when paired with midi-instrument
    private ArrayList<Element> midiDevice;        // minOccurs=0 maxOccurs="unbounded"

    // MIDI-INSTRUMENT
    private ArrayList<MidiInstrument> midiInstruments; // SUBTREE




    // PART - BODY
    private ArrayList<Measure> measureList;



    // CONSTRUCTOR
    public Part() {
        partNameDispElements = new ArrayList<Element>();
        partAbbrevDispElements = new ArrayList<Element>();
        group = new ArrayList<Element>();
        scoreInstElements = new ArrayList<Element>();
        virtualInstElements = new ArrayList<Element>();
        midiDevice = new ArrayList<Element>();
        midiInstruments = new ArrayList<MidiInstrument>();
    }


    // GETTERS
    public Attribute getPartIDAttribute() {
        return partIDAttribute;
    }

    public Identification getIdentification() {
        return identification;
    }

    public Element getPartName() {
        return partName;
    }

    public boolean getPartNameDisplay() {
        return partNameDisplay;
    }

    public Attribute getPartNameDispAttribute() {
        return partNameDispAttribute;
    }

    public ArrayList<Element> getPartNameDispElements() {
        return partNameDispElements;
    }

    public Element getPartAbbreviation() {
        return partAbbreviation;
    }

    public boolean getPartAbbreviationDisplay() {
        return partAbbreviationDisplay;
    }

    public Attribute getPartAbbrevDispAttribute() {
        return partAbbrevDispAttribute;
    }

    public ArrayList<Element> getPartAbbrevDispElements() {
        return partAbbrevDispElements;
    }

    public ArrayList<Element> getGroup() {
        return group;
    }

    public boolean getScoreInstrument() {
        return scoreInstrument;
    }

    public Attribute getScoreInstAttribute() {
        return scoreInstAttribute;
    }

    public boolean getVirtualInstrument() {
        return virtualInstrument;
    }

    public ArrayList<Element> getScoreInstElements() {
        return scoreInstElements;
    }

    public ArrayList<Element> getVirtualInstElements() {
        return virtualInstElements;
    }

    public ArrayList<Element> getMidiDevice() {
        return midiDevice;
    }

    public ArrayList<MidiInstrument> getMidiInstruments() {
        return midiInstruments;
    }


    // SETTERS
    public void setPartIDAttribute(Attribute a) {
        partIDAttribute = a;
    }

    public void setIdentification(Identification i) {
        identification = i;
    }

    public void setPartName(Element e) {
        partName = e;
    }

    public void setPartNameDisplay(boolean PND) {
        partNameDisplay = PND;
    }

    public void setPartNameDispAttribute(Attribute a) {
        partNameDispAttribute = a;
    }

    public void setPartAbbreviation(Element e) {
        partAbbreviation = e;
    }

    public void setPartAbbreviationDisplay(boolean PAD) {
        partAbbreviationDisplay = PAD;
    }

    public void setPartAbbrevDispAttribute(Attribute a) {
        partAbbrevDispAttribute = a;
    }

    public void setScoreInstrument(boolean SI) {
        scoreInstrument = SI;
    }

    public void setScoreInstAttribute(Attribute a) {
        scoreInstAttribute = a;
    }

    public void setVirtualInstrument(boolean VI) {
        virtualInstrument = VI;
    }




    // ADD TO
    public void addToPartNameDispElements(Element e) {
        partNameDispElements.add(e);
    }

    public void addToPartAbbrevDispElements(Element e) {
        partAbbrevDispElements.add(e);
    }

    public void addToGroup(Element e) {
        group.add(e);
    }

    public void addToScoreInstElements(Element e) {
        scoreInstElements.add(e);
    }

    public void addToVirtualInstElements(Element e) {
        virtualInstElements.add(e);
    }

    public void addToMidiDevice(Element e) {
        midiDevice.add(e);
    }

    public void addToMidiInstruments(MidiInstrument MI) {
        midiInstruments.add(MI);
    }


    public void addToMeasureList(Measure aMeasure) {
        measureList.add(aMeasure);
    }

}
