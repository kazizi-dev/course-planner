package ca.cmpt213.as5.model;

/*
    This class is for storing the semester number at the time that
    the course is being offered.
 */
public class Semester {
    private String semesterNum;

    public Semester(){}

    public Semester(String semesterNum){
        this.semesterNum = semesterNum;
    }

    public String getSemesterNum() {
        return semesterNum;
    }

    public void setSemesterNum(String semesterNum) {
        this.semesterNum = semesterNum;
    }

    public boolean equals(Semester semester){
        return getSemesterNum().equals(semester.getSemesterNum());
    }
}
