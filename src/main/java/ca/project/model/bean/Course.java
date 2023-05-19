package ca.project.model.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Course implements Comparable<Course>, Iterable<Offering>{
    private static final int SPRING = 1;
    private static final int SUMMER = 4;
    private static final int FALL = 7;
    private static final int YEAR_TRANSLATION = 1900;

    private String catalogNumber;
    private List<Offering> offerings;
    private int courseId;


    public Course(){
        offerings = new ArrayList<>();
    }

    public void addToOfferingList(Offering newOffering){
        Offering duplicateOffering = getDuplicateOffering(newOffering);

        if(duplicateOffering != null){
            duplicateOffering.addInstructorToList(newOffering.getInstructorsSet());
            for(Section section : newOffering.getSections()){
                duplicateOffering.addSectionToList(section);
            }
        }
        else {
            offerings.add(newOffering);
        }
    }

    private Offering getDuplicateOffering(Offering newOffering){
        for(Offering offering : offerings){
            final String location = offering.getLocation();
            final String locationNew = newOffering.getLocation();
            final int semCode = offering.getSemesterCode();
            final int semCodeNew = newOffering.getSemesterCode();

            if(location.equalsIgnoreCase(locationNew) && semCode == semCodeNew){
                return offering;
            }
        }
        return null;
    }


    public boolean equals(Course other){
        return catalogNumber.equals(other.catalogNumber);
    }

    // for debugging:
    public String toString(){
        return " CourseId: " + courseId + "\n" + " CatalogNum: " + catalogNumber + "\n";
    }

    @Override
    public int compareTo(Course o) {
        return catalogNumber.compareTo(o.catalogNumber);
    }

    @Override
    public Iterator<Offering> iterator() {
        return offerings.iterator();
    }
}
