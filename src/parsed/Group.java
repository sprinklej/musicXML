package parsed;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class Group {
    private String number;
    private String type;
    private String groupName;

    public Group(String aNumber, String aType) {
        number = aNumber;
        type = aType;
    }

    // GETTERS
    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getGroupName() {
        return groupName;
    }

    // SETTERS
    public void setNumber(String aNumber) {
        number = aNumber;
    }

    public void setType(String aType) {
        type = aType;
    }

    public void setGroupName(String aGroupName) {
        groupName = aGroupName;
    }

    public String toString() {
        String string = "";

        string += "group-type: " + type + "\n";
        string += "group-number: " + number + "\n";
        string += "group-name: " + groupName;

        return string;
    }
}
