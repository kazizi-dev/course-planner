package ca.cmpt213.as5.model;

/*
    This class stores the number of students
    that are currently enrolled in the class.
 */
public class EnrolmentTotal {
    private int enrolmentTotalNum;

    public EnrolmentTotal(){}

    public EnrolmentTotal(int enrolmentTotalNum) {
        this.enrolmentTotalNum = enrolmentTotalNum;
    }

    public int getEnrolmentTotalNum() {
        return enrolmentTotalNum;
    }

    public void setEnrolmentTotalNum(int enrolmentTotalNum) {
        this.enrolmentTotalNum = enrolmentTotalNum;
    }

    public void increaseEnrolmentTotal(int enrolmentTotalNum){
        this.enrolmentTotalNum += enrolmentTotalNum;
    }
}
