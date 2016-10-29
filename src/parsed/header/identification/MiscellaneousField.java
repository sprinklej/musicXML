package parsed.header.identification;

import parser.XMLConsts;

/**
 * Created by sprinklej on 2016-10-26.
 * FROM XSD: If a program has other metadata not yet supported in the MusicXML format, it can go in the miscellaneous
 * element. The miscellaneous type puts each separate part of metadata into its own miscellaneous-field type.
 */
public class MiscellaneousField {
    private String nameAttribute; // required
    private String text;

    // CONSTRUCTOR
    public MiscellaneousField(String aNameAtt) {
        nameAttribute = aNameAtt;
    }

    // GETTERS
    public String getNameAttribue() {
        return nameAttribute;
    }

    public String getText() {
        return text;
    }


    // SETTERS
    public void setNameAttribute(String aNameAtt) {
        nameAttribute = aNameAtt;
    }

    public void setText(String aText) {
        text = aText;
    }


    // TOSTRING
    public String toString() {
        String string = "<" + XMLConsts.MISCELLANEOUS_FIELD + " " + XMLConsts.NAME + "=\"" + nameAttribute + "\">"
                + text + "</" + XMLConsts.MISCELLANEOUS_FIELD + ">";
        return string;
    }
}
