package ca.project.model.bean;

import lombok.Data;

@Data
public class Graph implements Comparable<Graph> {
    private int semesterCode;
    private int totalCoursesTaken;

    public Graph(){}

    @Override
    public int compareTo(Graph o) {
        return semesterCode - o.semesterCode;
    }

    public void updateTotalCoursesTaken(Department department, int semesterCode){
        for(Course course : department.getCourses()){
            for(Offering offering : course.getOfferings()){
                if(offering.getSemesterCode() == semesterCode){
                    totalCoursesTaken += getTotalEnrollments(offering);
                }
            }
        }
    }

    private int getTotalEnrollments(Offering offering){
        int total = 0;
        for(Section section : offering){
            if(section.getType().equalsIgnoreCase("LEC")){
                total += section.getEnrollmentTotal();
            }
        }
        return total;
    }
}
