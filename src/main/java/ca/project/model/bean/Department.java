package ca.project.model.bean;

import lombok.Data;

import java.util.List;
import java.util.LinkedList;


@Data
public class Department implements Comparable<Department> {
    private String name;
    private int deptId;
    private List<Course> courses;

    public Department(){
        courses = new LinkedList<>();
    }

    public void addCourseToList(Course newCourse){
        Course duplicateCourse = getDuplicateCourse(newCourse);

        // group offerings of the same course
        if(duplicateCourse != null){
            int i = newCourse.getOfferings().size() - 1;
            duplicateCourse.addToOfferingList(newCourse.getOfferings().get(i));
        }
        else {
            courses.add(newCourse);
        }
    }

    @Override
    public int compareTo(Department other) {
        return this.name.compareTo(other.getName());        // for sort collection
    }

    public boolean equals(Department other){
        return name.equalsIgnoreCase(other.getName());
    }

    // ------------ Helper functions ------------
    private Course getDuplicateCourse(Course newCourse){
        for(Course course : courses){
            if(course.equals(newCourse)){
                return course;
            }
        }
        return null;
    }


    public int getFirstSemesterCode() {
        int firstSemester = 0;
        for (Course eachCourse: courses) {
            int semesterCode = eachCourse.getOfferings().get(0).getSemesterCode();

            if (semesterCode < firstSemester || firstSemester == 0) {
                firstSemester = semesterCode;
            }
        }

        return firstSemester;
    }

    public int getLastSemesterCode() {
        int lastSemester = 0;
        for (Course eachCourse: courses) {
            int semesterCode = eachCourse.getOfferings().get(eachCourse.getOfferings().size() - 1).getSemesterCode();

            if (semesterCode > lastSemester) {
                lastSemester = semesterCode;
            }
        }

        return lastSemester;
    }
}
