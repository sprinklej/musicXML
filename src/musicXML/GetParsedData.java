
/*
 * Get some parsed data from the Score to display
 */

package musicXML;

import parsed.*;

public class GetParsedData {
    private Score score;

    // Constructor
    public GetParsedData(Score aScore) {
        score = aScore;
    }

    public String getScoreType() {
        return "Score Type: " + score.getScoreType() + "\n";
    }

    // Work Info
    public String getWorkInfo() {
        String str = "";

        if (score.getWork() != null) {
            for (ElementWrapper el : score.getWork().getElements()) {
                if (el.getElement().getElementName().contentEquals("work-number")) {
                    str += "Work-number: " + el.getElement().getData() + "\n";
                }
                if (el.getElement().getElementName().contentEquals("work-title")) {
                    str += "Work-title: " + el.getElement().getData() + "\n";
                }
            }
        }
        return str;
    }

    // movement-number
    public String getMovementNumber() {
        if (score.getMovementNumberElement() != null) {
            return "Movement-number: " + score.getMovementNumberElement().getData() + "\n";
        }
        return "";
    }

    // movement-title
    public String getMovementTitle() {
        if (score.getMovementTitleElement() != null) {
            return "Movement-title: " + score.getMovementTitleElement().getData() + "\n";
        }
        return "";
    }

    // identification
    public String getIdentification() {
        String str = "";
        if (score.getIdentification() != null) {
            for (ElementWrapper el:score.getIdentification().getElements()) {
                if ((el.getIsComplex() == false) && (el.getElement().getElementName().contentEquals("creator"))) {
                    str += "Creator-" + el.getElement().getAttributes().get(0).getAttributeText()
                            + ": " + el.getElement().getData() + "\n"; // only 1 attribute for creator
                }
                if ((el.getIsComplex() == false) && (el.getElement().getElementName().contentEquals("rights"))) {
                    str += "Rights: " + el.getElement().getData() + "\n";
                }
            }
        }
        return str;
    }


    // part Info
    public String getPartInfo() {
        String str = "";

        if (score.getPartList() != null) {
            for (ElementWrapper el:score.getPartList().getElements()) {
                if ((el.getIsComplex() == true) && (el.getComplexElement().getElementName().contentEquals("score-part"))) {
                    ComplexElement ce = el.getComplexElement();
                    for (Attribute a:ce.getAttributes()) {
                        if (a.getAttributeName().contentEquals("id")) {
                           str += "Part ID: " + a.getAttributeText() + "\n";
                        }
                    }

                    for (ElementWrapper e:ce.getElements()) {
                        if ((e.getIsComplex() == false) && (e.getElement().getElementName().contentEquals("part-name"))) {
                            str += "Part-name: " + e.getElement().getData() + "\n";
                        }
                    }
                }
            }
        }
        return str;
    }
}

