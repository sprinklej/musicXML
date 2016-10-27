package parsed.header;

import parsed.TypedText;

import java.util.ArrayList;

/**
 * Created by sprinklej on 2016-10-26.
 * FROM XSD: The encoding element contains information about who did the digital encoding, when, with what software,
 * and in what aspects. Standard type values for the encoder element are music, words, and arrangement, but other
 * types may be used. The type attribute is only needed when there are multiple encoder elements.
 */
public class Encoding {
    private ArrayList<String> encodingDate;    // minOccurs=0 maxOccurs="unbounded" - format: yyyy-mm-dd
    private ArrayList<TypedText> encoder;      // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<String> software;        // minOccurs=0 maxOccurs="unbounded"
    private ArrayList<String> encodDescription;// minOccurs=0 maxOccurs="unbounded"
    private ArrayList<Supports> supports;      // minOccurs=0 maxOccurs="unbounded" - gets its own class as it has lots of values

    // CONSTRUCTOR
    public Encoding() {
        encodingDate = new ArrayList<String>();
        encoder = new ArrayList<TypedText>();
        software = new ArrayList<String>();
        encodDescription = new ArrayList<String>();
        supports = new ArrayList<Supports>();
    }


    // GETTERS
    public ArrayList<String> getEncodingDate() {
        return encodingDate;
    }

    public ArrayList<TypedText> getEncoder() {
        return encoder;
    }

    public ArrayList<String> getSoftware() {
        return software;
    }

    public ArrayList<String> getEncodDescription() {
        return encodDescription;
    }

    public ArrayList<Supports> getSupports() {
        return supports;
    }


    // ADD TO
    public void addToEncodingDate(String aDate) {
        encodingDate.add(aDate);
    }

    public void addToEncoder(TypedText aEncoder) {
        encoder.add(aEncoder);
    }

    public void addToSoftware(String aSoftware) {
        software.add(aSoftware);
    }

    public void addToEncodDescription(String aEncodDescrip) {
        encodDescription.add(aEncodDescrip);
    }

    public void addToSupports(Supports aSupport) {
        supports.add(aSupport);
    }
}