package ca.project.model.bean;

import lombok.Data;

@Data
public class Section{
    private int enrollmentTotal;
    private int enrollmentCap;
    private String type;

    public void increaseEnrollmentTotal(int count){
        this.enrollmentTotal += count;
    }

    public void increaseEnrollmentCap(int count){
        this.enrollmentCap += count;
    }

    public boolean equals(Section newSection){
        return newSection.type.equalsIgnoreCase(newSection.getType());
    }
}
