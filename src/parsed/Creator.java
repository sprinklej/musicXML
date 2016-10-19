package parsed;

/**
 * Created by sprinklej on 2016-10-19.
 */
public class Creator {
    private String type;
    private String creator;

    //
    public Creator(String aType, String aCreator) {
        type = aType;
        creator = aCreator;
    }

    // GETTERS
    public String getCreator() {
        return creator;
    }

    public String getType() {
        return type;
    }

    // SETTERS
    public void setCreator(String aCreator) {
        creator = aCreator;
    }

    public void setType(String aType) {
        type = aType;
    }
}
