package parsed.header.partlist;

import parsed.Attribute;
import parsed.Element;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-29.
 */
public class MidiInstrument {
    private Attribute midiAttribute;
    private ArrayList<Element> midiIntrumentElements;


    // CONSTRUCTOR
    public MidiInstrument() {
        midiIntrumentElements = new ArrayList<Element>();
    }


    // GETTERS
    public Attribute getMidiAttribute() {
        return midiAttribute;
    }

    public ArrayList<Element> getMidiIntrumentElements() {
        return midiIntrumentElements;
    }


    // SETTERS
    public void setMidiAttribute(Attribute a) {
        midiAttribute = a;
    }

    // ADD TO
    public void addToMidiInstrumentElements(Element e) {
        midiIntrumentElements.add(e);
    }
}
