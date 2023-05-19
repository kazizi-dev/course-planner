package ca.cmpt213.as5.model;

/*
    This class stores the subject name that
    is being offered by the school.
 */

import ca.cmpt213.as5.model.Courses.CoursePlanner;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "subjectId", "name"})
public class Department implements Comparable<Department>{
    private String name;
    private long deptId;
    private List<CoursePlanner> coursePlannerList;

    public List<CoursePlanner> getCoursePlannerList() {
        return this.coursePlannerList;
    }
    public void addCoursePlanner(CoursePlanner coursePlanner) {
        this.coursePlannerList.add(coursePlanner);
    }

    public Department(){
        coursePlannerList = new ArrayList<>();
    }

    public Department(String name) {
        coursePlannerList = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDepartmentName(String subjectName) {
        this.name = subjectName;
    }

    public void setDeptId(long subjectId){
        this.deptId = subjectId;
    }

    public long getDeptId(){
        return deptId;
    }

    public boolean isSubjectTheSame(String subjectName){
        return this.name.equals(subjectName);
    }

    @Override
    public int compareTo(Department other){
        return this.getName().compareTo(other.getName());     // for sort collection
    }

    public boolean equals(Department other){
        return other.name.equalsIgnoreCase(name);
    }
}
