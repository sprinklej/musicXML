package parser;

import parsed.Part;
import parsed.header.partlist.Group;

/**
 * Created by sprinklej on 2016-10-06.
 */
public class PartListWrapper {
    private boolean isPart;

    private Part part = null;
    private Group group = null;

    public PartListWrapper(boolean aIsPart, Object aObject) {
        isPart = aIsPart;

        if (isPart == true) {
            part = (Part) aObject;
        } else {
            group = (Group) aObject;
        }
    }


    // GETTERS
    public boolean getIsPart() {
        return isPart;
    }

    public Part getPart() {
        return part;
    }

    public Group getGroup() {
        return group;
    }

}
