package ca.cmpt213.as5.Organize_Data;


import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.model.CatalogNumber;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    This class merges the information about the same courses together.
*/

public class MergeCourses {
    private List<CoursePlanner> courseList = new ArrayList<>();
    private List<CoursePlanner> sameCourseList = new ArrayList<>();

    // contains the courses that have already been calculated
    private List<CoursePlanner> alreadyChosenCourseList = new ArrayList<>();

    private Department department;
    private CatalogNumber catalog;

    public MergeCourses(){

    }

    public MergeCourses(List<CoursePlanner> courseList, Department department,
                        String catalogNumStr) throws CourseNotFoundException {
        this.courseList = courseList;
        this.department = department;
        this.catalog = new CatalogNumber();
        catalog.setCatalogNum(catalogNumStr);
    }

    public void setAllCourseList(List<CoursePlanner> courseList){
        this.courseList = courseList;
    }

    public void setSameCourseList(List<CoursePlanner> sameCourseList){
        this.sameCourseList = sameCourseList;
    }

    public void setSameSubject(Department department){
        this.department = department;
    }

    public void setCatalog(CatalogNumber catalog){
        this.catalog = catalog;
    }

    public CatalogNumber getCatalog(){
        return catalog;
    }

    public void mergeSameCourses() throws CourseNotFoundException {
        if(!courseList.isEmpty()){
            for(CoursePlanner course : courseList){
                boolean courseExistCondition = isCourseEqual(course);
                boolean catalogCondition = course.getCatalog().getCatalogNum().equals(catalog.getCatalogNum());
                boolean subjectCondition = course.getDepartment().getName().equals(department.getName());

                if(!courseExistCondition && subjectCondition && catalogCondition){
                    sameCourseList.add(course);
                }
            }
        }
        else{
            throw new CourseNotFoundException("Course List is Empty! (1)");
        }
    }

    public List<CoursePlanner> getAlreadyChosenCourseList(List<CoursePlanner> sortedSameCourseList,
                                                          CatalogNumber catalogObj) throws CourseNotFoundException {
        if(!sortedSameCourseList.isEmpty()){
            for(CoursePlanner course : sortedSameCourseList){
                boolean alreadyChosenCourseCondition = alreadyChosenCourseList.contains(course);
                boolean sameCatalogCondition = course.getCatalog().getCatalogNum().equals(catalogObj.getCatalogNum());
                if(!alreadyChosenCourseCondition && sameCatalogCondition){
                    alreadyChosenCourseList.add(course);
                }
            }
            return alreadyChosenCourseList;
        }
        else{
            throw new CourseNotFoundException("Error: Course List is Empty! (2)");
        }
    }

    public List<CoursePlanner> getSameCourseListSorted() throws CourseNotFoundException {
        if(!sameCourseList.isEmpty()) {
            Collections.sort(sameCourseList);
        }
        else{
            throw new CourseNotFoundException("Error: Course List is Empty! (3)");
        }
        return sameCourseList;
    }

    // ....................... Helper functions ......................... //
    private boolean isCourseEqual(CoursePlanner targetCourse) throws CourseNotFoundException {
        if(!sameCourseList.isEmpty()){
            for(CoursePlanner existingCourse : sameCourseList){
                if(isTheSameAs(existingCourse, targetCourse)){
                    mergeSameCourseInfo(existingCourse, targetCourse);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isTheSameAs(CoursePlanner existingCourse, CoursePlanner newCourse)
            throws CourseNotFoundException {

        if(existingCourse != null && newCourse != null){
            boolean isSameCatalogNum = existingCourse.getCatalog().getCatalogNum()
                    .equals(newCourse.getCatalog().getCatalogNum());

            boolean isSameComponent = existingCourse.getComponent().getComponentCode()
                    .equals(newCourse.getComponent().getComponentCode());

            boolean isSameLocation = existingCourse.getLocation().getCampusLocName()
                    .equals(newCourse.getLocation().getCampusLocName());

            boolean isSameSemester = existingCourse.getSemester().getSemesterNum()
                    .equals(newCourse.getSemester().getSemesterNum());

            boolean isSameSubject = existingCourse.getDepartment().getName()
                    .endsWith(newCourse.getDepartment().getName());

            return isSameCatalogNum && isSameComponent && isSameLocation && isSameSemester && isSameSubject;
        }
        else{
            throw new CourseNotFoundException("Course is null, so course is not found in MergeCourses class.");
        }
    }

    private void mergeSameCourseInfo(CoursePlanner existingCourse, CoursePlanner newSameCourse){
        // increment enrolment total
        existingCourse.getEnrolmentTotalObj().increaseEnrolmentTotal(
                newSameCourse.getEnrolmentTotalObj().getEnrolmentTotalNum());

        // increment enrolment cap
        existingCourse.getEnrolMentCapObj().increaseEnrolmentCapacity(
                newSameCourse.getEnrolMentCapObj().getEnrolmentCap());

        // add the instructors
        existingCourse.getInstructor().setAllInstructors(
                newSameCourse.getInstructor().getAllInstructors());
    }
}
