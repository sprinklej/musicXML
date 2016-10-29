package parsed.header.identification;

/**
 * Created by sprinklej on 2016-10-26.
 *
 * Is a self closing tag that is all attributes
 *
 * FROM XSD: The supports type indicates if a MusicXML encoding supports a particular MusicXML element. This is
 * recommended for elements like beam, stem, and accidental, where the absence of an element is ambiguous if you
 * do not know if the encoding supports that element. For Version 2.0, the supports element is expanded to allow
 * programs to indicate support for particular attributes or particular values. This lets applications communicate,
 * for example, that all system and/or page breaks are contained in the MusicXML file.
 */
public class Supports {
    // TODO GET RID OF - MOVE TO ATTRIBUTE MODEL

    private String typeAttribute;      // Required, type = yes|no
    private String elementAttribute;   // Required
    private String attributeAttribute;
    private String valueAttribute;

    // CONSTRUCTOR
    public Supports() {}


    // GETTERS
    public String getTypeAttribute() {
        return typeAttribute;
    }

    public String getElementAttribute() {
        return elementAttribute;
    }

    public String getAttributeAttribute() {
        return attributeAttribute;
    }

    public String getValueAttribute() {
        return valueAttribute;
    }


    // SETTERS
    public void setTypeAttribute(String aTypeAtt) {
        typeAttribute = aTypeAtt;
    }

    public void setElementAttribute(String aElementAtt) {
        elementAttribute = aElementAtt;
    }

    public void setAttributeAttribute(String aAttributeAtt) {
        attributeAttribute = aAttributeAtt;
    }

    public void setValueAttribute(String aValueAtt) {
        valueAttribute = aValueAtt;
    }
}
