package ca.cmpt213.as5.model;

/*
    This class stores the information about the total number of students that are
    allowed to enrol in a lecture or lab.
 */
public class EnrolmentCapacity {
    private int enrolmentCap;

    public EnrolmentCapacity(){}

    public EnrolmentCapacity(int enrolmentCap) {
        this.enrolmentCap = enrolmentCap;
    }

    public int getEnrolmentCap() {
        return enrolmentCap;
    }

    public void setEnrolmentCap(int enrolmentCap) {
        this.enrolmentCap = enrolmentCap;
    }

    public void increaseEnrolmentCapacity(int additionalCap){
        this.enrolmentCap += additionalCap;
    }
}
