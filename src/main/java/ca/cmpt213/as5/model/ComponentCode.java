package ca.cmpt213.as5.model;

/*
    This class stores the information about whether a course
    is of type lecture, tutorial, etc.
 */
public class ComponentCode{
    private String componentCode;

    public ComponentCode(){}

    public ComponentCode(String componenetCode) {
        this.componentCode = componenetCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public boolean equals(ComponentCode other){
        return componentCode.equalsIgnoreCase(other.componentCode);
    }
}
