package ca.cmpt213.as5.model.Courses;

/*
    This class will calculate the enrolment field for
    the output curl commands used by the user.
 */
public class CourseEnrollmentData {

    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public CourseEnrollmentData(){}

    public CourseEnrollmentData(CourseOffering course){
        this.enrollmentCap = (course.getEnrollmentCap());
        this.enrollmentTotal = (course.getEnrollmentTotal());
        this.type = (course.getType());
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }


}
