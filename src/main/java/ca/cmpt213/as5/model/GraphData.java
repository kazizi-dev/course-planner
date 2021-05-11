package ca.cmpt213.as5.model;

/*
    This class is used to set the information to build a UI graph.
 */
public class GraphData {
    private int semesterCode;
    private int totalCoursesTaken;

    public GraphData(int semesterCode, int totalCoursesTaken){
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
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
