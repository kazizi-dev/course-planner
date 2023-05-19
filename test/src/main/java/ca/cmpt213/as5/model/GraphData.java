package ca.cmpt213.as5.model;


import ca.cmpt213.as5.model.Courses.CourseOffering;
import ca.cmpt213.as5.model.Courses.CoursePlanner;

import java.util.List;

/**
 * GraphData class holds information about student enrollment for the graph on the server to display
 */
public class GraphData implements Comparable<GraphData> {
    private int semesterCode;
    private int totalCoursesTaken;

    @Override
    public int compareTo(GraphData other) {
        return semesterCode - other.semesterCode;
    }

    public GraphData(int semesterCode, List<CourseOffering> offerings) {
        this.semesterCode = semesterCode;

        for (CourseOffering offering : offerings) {
            if (offering.getSemesterCode() == semesterCode) {
                totalCoursesTaken += offering.getEnrollmentTotal();
            }
        }
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(int semesterCode) {
        this.semesterCode = semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void setTotalCoursesTaken(int totalCoursesTaken) {
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
