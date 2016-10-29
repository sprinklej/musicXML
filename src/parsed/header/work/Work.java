package parsed.header.work;

/**
 * Created by sprinklej on 2016-10-15.
 * FROM XSD: Works are optionally identified by number and title. The work type also may indicate a
 * link to the opus document that composes multiple scores into a collection.
 */
public class Work {
    private String workNumber;    //type="xs:string"
    private String workTitle;     //type="xs:string"
    private boolean opus = false; //type="opus" - Is a self closing tag that is all attributes
    private LinkAttributes opusAttributes;

    // CONSTRUCTOR
    public Work() {}

    // GETTERS
    public String getWorkNumber() {
        return workNumber;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public boolean getOpus() {
        return opus;
    }

    public LinkAttributes getOpusAttributes() {
        return opusAttributes;
    }

    // SETTERS
    public void setWorkNumber(String aWorkNumber) {
        workNumber = aWorkNumber;
    }

    public void setWorkTitle(String aWorkTitle) {
        workTitle = aWorkTitle;
    }

    public void setOpus(boolean aOpus) {
        opus = aOpus;
    }

    public void setOpusAttributes(LinkAttributes aOpusAttributes) {
        opusAttributes = aOpusAttributes;
    }
}
