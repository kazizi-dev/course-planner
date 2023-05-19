package ca.cmpt213.as5.model.Courses;

/*
 * holds data needed by the frontend UI, will be built from the backend
 */

public class CourseOffering {
    private static final int SPRING = 1;
    private static final int SUMMER = 4;
    private static final int FALL = 7;
    private static final int YEAR_TRANSLATION = 1900;
    private Integer courseOfferingId;
    private ComputeCourseOutput course;
    private String location;
    private String instructors;
    private String term;
    private Integer semesterCode;
    private Integer year;
    private Integer enrollmentCap;
    private Integer enrollmentTotal;
    private String type;


    public CourseOffering(Integer courseOfferingId, String location, String instructors,
                          String term, Integer year, Integer semesterCode){
        setCourseOfferingId(courseOfferingId);
        setInstructors(instructors);
        setLocation(location);
        setSemesterCode(semesterCode);
        setTerm(term);
        setYear(year);
    }
    public CourseOffering(CoursePlanner course){
        setCourseOfferingId(course.getCourseId());
        setInstructors(course.getInstructor().getAllInstructorsStr());
        setLocation(course.getLocation().getCampusLocName());
        setSemesterCode(Integer.parseInt(course.getSemester().getSemesterNum()));
        int term = semesterCode%10;
        if(term == FALL){
            setTerm("FALL");
        }else if(term == SUMMER){
            setTerm("SUMMER");
        }else if(term == SPRING){
            setTerm("SPRING");
        }
        Integer year = semesterCode/10;
        year+=YEAR_TRANSLATION;
        setYear(year);
        setCourse(new ComputeCourseOutput(course));
        setEnrollmentTotal(course.getEnrolmentTotalObj().getEnrolmentTotalNum());
        setEnrollmentCap(course.getEnrolMentCapObj().getEnrolmentCap());
        setType(course.getComponent().getComponentCode());
    }

    public Integer getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Integer courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstructors() {
        return instructors;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(Integer semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }


    public ComputeCourseOutput getCourse() {
        return course;
    }

    public void setCourse(ComputeCourseOutput course) {
        this.course = course;
    }

    public Integer getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(Integer enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public Integer getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(Integer enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
