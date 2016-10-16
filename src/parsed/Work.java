package parsed;

/**
 * Created by sprinklej on 2016-10-15.
 */
public class Work {
    private String workNumber; //no attributes
    private String workTitle; //no attributes
    private String opus; //TODO - attributes

    // CONSTRUCTOR
    public Work() {}

    // GETTERS
    public String getWorkNumber() {
        return workNumber;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public String getWorkOpus() {
        return opus;
    }

    // SETTERS
    public void setWorkNumber(String aWorkNumber) {
        workNumber = aWorkNumber;
    }

    public void setWorkTitle(String aWorkTitle) {
        workTitle = aWorkTitle;
    }

    public void setWorkOpus(String aOpus) {
        opus = aOpus;
    }


    // TOSTRING
    public String toString() {
        String string = "";
        string = "workNumber: " + workNumber + "\n";
        string += "workTitle: " + workTitle + "\n";
        string += "opus: " + opus;
        return string;
    }
}
