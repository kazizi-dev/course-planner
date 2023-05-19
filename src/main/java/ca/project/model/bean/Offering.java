package ca.project.model.bean;

import lombok.Data;

import java.util.*;

@Data
public class Offering implements Comparable<Offering>, Iterable<Section> {
    private int courseOfferingId;
    private String location;
    private Set<String> instructors;
    private int year;
    private int semesterCode;
    private String term;
    private List<Section> sections;

    private static final int SPRING = 1;
    private static final int SUMMER = 4;
    private static final int FALL = 7;
    private static final int YEAR_TRANSLATION = 1900;


    public Offering(){
        instructors = new HashSet<>();
        sections = new ArrayList<>();
    }

    public int getYear(){
        int year = semesterCode / 10;
        year += YEAR_TRANSLATION;
        return year;
    }

    public String getTerm(){
        int term = semesterCode % 10;
        if(term == FALL){
            return ("FALL");
        }
        else if(term == SUMMER){
            return ("SUMMER");
        }
        else if(term == SPRING){
            return ("SPRING");
        }
        return null;
    }


    public void addInstructorToList(Instructor newInstructor){
        // collecting the number of instructors of each course offering
        instructors.add(newInstructor.getFullName());
    }

    public void addInstructorToList(Set<String> newInstructors){
        // collecting the number of instructors of each course offering
        instructors.addAll(newInstructors);
    }

    public String getInstructors() {
        if (instructors.size() == 1) {
            for(String instructor : instructors){
                return instructor;
            }
        }

        StringBuilder instructorsAsString = new StringBuilder();
        int i = 0;
        for(String instructor : instructors){
            if(i == 0){
                instructorsAsString.append(instructor);
            }
            else {
                instructorsAsString.append(", ").append(instructor);
            }
            i++;
        }
        return instructorsAsString.toString();
    }

    public Set<String> getInstructorsSet() {
        return instructors;
    }

    public void addSectionToList(Section newSection){
        Section duplicateSection = getDuplicateSection(newSection);

        // group the enrollments from the same course
        if(duplicateSection != null){
            duplicateSection.increaseEnrollmentTotal(newSection.getEnrollmentTotal());
            duplicateSection.increaseEnrollmentCap(newSection.getEnrollmentCap());
        }
        else {
            sections.add(newSection);
        }
    }

    private Section getDuplicateSection(Section newSection){
        for(Section section : sections){
            if(section.equals(newSection)){
                return section;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Offering other) {
        return semesterCode - other.semesterCode;
    }

    @Override
    public Iterator<Section> iterator() {
        return sections.iterator();
    }
}