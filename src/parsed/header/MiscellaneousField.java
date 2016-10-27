package parsed.header;

import parser.XMLConsts;

/**
 * Created by sprinklej on 2016-10-26.
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
