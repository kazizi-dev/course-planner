package ca.cmpt213.as5.model.Courses;

/*
    This class stores an information required by the right
    class. It allows the user to set instances of different
    classes, and to be able to receive them.
 */

import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.watchers.IObservable;
import ca.cmpt213.as5.watchers.IObserver;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CoursePlanner implements Comparable<CoursePlanner>, IObservable {
    private static final int SPRING = 1;
    private static final int SUMMER = 4;
    private static final int FALL = 7;
    private static final int YEAR_TRANSLATION = 1900;
    private List<IObserver> watchers = new ArrayList<>();
    private CatalogNumber catalogNumObj;
    private ComponentCode componentCode;
    private EnrolmentCapacity enrolmentCapObj;
    private EnrolmentTotal enrolmentTotalObj;
    private Instructor instructor;
    private Location location;
    private Semester semester;
    private Department department;

    private Integer courseId;

    public CoursePlanner(String semesterVal, String departmentVal, String catalogNumberVal,
                         String locationVal, Integer enrolmentCapacityVal, String componentCodeVal,
                         Integer enrolmentTotalVal, String instructorVal){

        catalogNumObj = new CatalogNumber();
        componentCode = new ComponentCode();
        enrolmentCapObj = new EnrolmentCapacity();
        enrolmentTotalObj = new EnrolmentTotal();
        instructor = new Instructor();
        location = new Location();
        semester = new Semester();
        department = new Department();
        semester.setSemesterNum(semesterVal);
        department.setDepartmentName(departmentVal);
        catalogNumObj.setCatalogNum(catalogNumberVal);
        location.setCampusLocName(locationVal);
        enrolmentCapObj.setEnrolmentCap(Integer.parseInt(String.valueOf(enrolmentCapacityVal)));
        componentCode.setComponentCode(componentCodeVal);
        enrolmentTotalObj.setEnrolmentTotalNum(Integer.parseInt(String.valueOf(enrolmentTotalVal)));
        instructor.setFullName(instructorVal);
        notifyObservers();
    }

    public CoursePlanner(){
        catalogNumObj = new CatalogNumber();
        componentCode = new ComponentCode();
        enrolmentCapObj = new EnrolmentCapacity();
        enrolmentTotalObj = new EnrolmentTotal();
        instructor = new Instructor();
        location = new Location();
        semester = new Semester();
        department = new Department();
    }

    @JsonIgnore
    public int getSemesterNum(){
        return Integer.parseInt(semester.getSemesterNum());
    }

    public CatalogNumber getCatalog() {
        return catalogNumObj;
    }

    public void setCatalog(CatalogNumber catalogNumber) {
        this.catalogNumObj = catalogNumber;
    }

    public ComponentCode getComponent() {
        return componentCode;
    }

    public void setComponent(ComponentCode componentCode) {
        this.componentCode = componentCode;
    }

    public EnrolmentCapacity getEnrolMentCapObj() {
        return enrolmentCapObj;
    }

    public void setEnrolmentCapacity(EnrolmentCapacity enrolmentCapacity) {
        this.enrolmentCapObj = enrolmentCapacity;
    }

    public EnrolmentTotal getEnrolmentTotalObj() {
        return enrolmentTotalObj;
    }

    public void setEnrolmentTotal(EnrolmentTotal enrolmentTotal) {
        this.enrolmentTotalObj = enrolmentTotal;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }


    public void setCourseId(Integer courseId){
        this.courseId = courseId;
    }

    public Integer getCourseId(){
        return courseId;
    }

    @Override
    public int compareTo(CoursePlanner other) {
        return this.getDepartment().compareTo(other.getDepartment());
    }

    // for debugging:
    public String toString(){
        return " CourseId: " + getCourseId() + "\n"
                + " Catalog Num: " + getCatalog().getCatalogNum() + "\n"
                + " Department: " + getDepartment().getName();
    }
    public int year(){
        Integer year = Integer.parseInt(getSemester().getSemesterNum())/10;
        year+=YEAR_TRANSLATION;
        return year;
    }

    public String term(){
        int term = Integer.parseInt(getSemester().getSemesterNum())%10;
        if(term == FALL){
            return ("FALL");
        }else if(term == SUMMER){
            return ("SUMMER");
        }else if(term == SPRING){
            return ("SPRING");
        }
        return null;
    }

    public boolean equals(CoursePlanner other){

        //private CatalogNumber catalogNumObj;
        //    private ComponentCode componentCode;
        //    private EnrolmentCapacity enrolmentCapObj;
        //    private EnrolmentTotal enrolmentTotalObj;
        //    private Instructor instructor;
        //    private Location location;
        //    private Semester semester;
        //    private Department department


        return catalogNumObj.equals(other.catalogNumObj) && department.equals(other.department);
    }

    @Override
    public void addObserver(IObserver e) {
        watchers.add(e);
    }

    @Override
    public void removeObserver(IObserver e) {
        int indexToDelete = watchers.indexOf(e);
        watchers.remove(indexToDelete);
    }

    @Override
    public void notifyObservers() {
        for (IObserver watcher : watchers) {
            watcher.update(this);
        }
    }
}
