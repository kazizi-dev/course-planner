package ca.cmpt213.as5.model;

/*
    This class stores the information about the
    location of the class that is being offered.
    The location is referring to the location of
    campuses that the university has.
 */
public class Location {
    private String campusLocName;

    public Location(){}

    public Location(String campusLocName) {
        this.campusLocName = campusLocName;
    }

    public String getCampusLocName() {
        return campusLocName;
    }

    public void setCampusLocName(String campusLocName) {
        this.campusLocName = campusLocName;
    }
}
