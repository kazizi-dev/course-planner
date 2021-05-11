package ca.cmpt213.as5.model;

/*
    This class stores the subject name that
    is being offered by the school.
 */

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "subjectId", "name"})
public class Department implements Comparable<Department>{
    private String name;
    private long deptId;

    public Department(){}

    public Department(String name) {
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
