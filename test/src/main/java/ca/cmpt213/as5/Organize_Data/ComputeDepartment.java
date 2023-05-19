package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.Courses.ComputeCourseOutput;
import ca.cmpt213.as5.model.Courses.CourseOffering;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    This class stores the information about the list of the courses
    and the list of the subjects that are present for the courses.
    It adds subjects that are equal to the department list.
 */
public class ComputeDepartment {
    private List<CoursePlanner> courseList = new ArrayList<>();
    private List<Department> allSubjectsList = new ArrayList<>();
    private Department department = new Department();

    public ComputeDepartment(){}

    public ComputeDepartment(List<CoursePlanner> courseList){
        this.courseList = courseList;
    }

    public void setDepartment(Department department){
        this.department = department;
    }

    public Department getDepartment(){
        return department;
    }

    public List<Department> getAllDepartmentsListSorted() throws CourseNotFoundException {
        if(!courseList.isEmpty()) {
            Collections.sort(allSubjectsList);      // uses comparable
        }
        else{
            throw new CourseNotFoundException("department list is empty inside ComputeDepartment class");
        }
        return allSubjectsList;
    }

    public void addAllSubjectsToList() throws CourseNotFoundException, ResourceNotFoundException {
        if(!courseList.isEmpty()){
            for(CoursePlanner course : courseList){
                checkSubjectAdditionToList(course);
            }
        }
        else{
            throw new CourseNotFoundException("Course list is empty inside ComputeDepartment class");
        }
    }

    public  List<ComputeCourseOutput> getListOfDepartmentCourses(List<CoursePlanner> courseList, int departmentId)
            throws ResourceNotFoundException, IOException, CourseNotFoundException {

        List<ComputeCourseOutput> listOfCoursesInOneDepartment = new ArrayList<>();
        ComputeDepartment computeDepartment = new ComputeDepartment(courseList);
        computeDepartment.addAllSubjectsToList();
        for(Department departmentObj : computeDepartment.getAllDepartmentsListSorted()){
            if(departmentObj.getDeptId() == ((departmentId))){
                ComputeCatalog computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
                computeCatalogObj.addAllCatalogNumsToList();
                for(CoursePlanner coursePlannerObj : computeCatalogObj.getSameSubjectCourseList()) {
                    listOfCoursesInOneDepartment.add(new ComputeCourseOutput(coursePlannerObj));
                }
            }
        }
        return listOfCoursesInOneDepartment;
    }

    public  List<CoursePlanner> getListOfDepartmentCourseOfferings(List<CoursePlanner> courseList, int departmentId)
            throws ResourceNotFoundException, IOException, CourseNotFoundException {

        List<CoursePlanner> listOfCoursesInOneDepartment = new ArrayList<>();
        ComputeDepartment computeDepartment = new ComputeDepartment(courseList);
        computeDepartment.addAllSubjectsToList();
        for(Department departmentObj : computeDepartment.getAllDepartmentsListSorted()){
            if(departmentObj.getDeptId() == ((departmentId))){
                ComputeCatalog computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
                computeCatalogObj.addAllCatalogNumsToList();
                for(CoursePlanner coursePlannerObj : computeCatalogObj.getSameSubjectCourseList()) {
                    listOfCoursesInOneDepartment.add((coursePlannerObj));
                }
            }
        }
        return listOfCoursesInOneDepartment;
    }

    public List<CourseOffering> getListOfDepartmentCoursesOfType(List<CoursePlanner> courseList, long CourseId){
        List<CourseOffering> listOfDepartmentCoursesOfType  = new ArrayList<>();
        for (CoursePlanner course : courseList) {
            long item = course.getCourseId();
            if(item == CourseId){
                listOfDepartmentCoursesOfType.add(new CourseOffering(course));
            }
        }
        return listOfDepartmentCoursesOfType;
    }
    public List<CoursePlanner> getListOfDepartmentCoursePlannersOfType(List<CoursePlanner> courseList, long CourseId){
        List<CoursePlanner> listOfDepartmentCoursesOfType  = new ArrayList<>();
        for (CoursePlanner course : courseList) {
            long item = course.getCourseId();
            if(item == CourseId){
                listOfDepartmentCoursesOfType.add((course));
            }
        }
        return listOfDepartmentCoursesOfType;
    }

    // ........................ Helper Functions ........................ //
    private void checkSubjectAdditionToList(CoursePlanner course)
            throws CourseNotFoundException, ResourceNotFoundException {

        if(course != null){
            boolean subjectCondition = isSubjectEqual(allSubjectsList, course.getDepartment());
            if(!subjectCondition){
                allSubjectsList.add(course.getDepartment());
            }
        }
        else{
            throw new CourseNotFoundException("Course not found inside ComputeDepartment");
        }
    }
    private boolean isSubjectEqual(List<Department> allSubjectsList, Department targetDepartment)
            throws ResourceNotFoundException {

        if(targetDepartment != null){
            if(!allSubjectsList.isEmpty()){
                for(Department department : allSubjectsList){
                    boolean subjectEqualCondition = department.getName().equals(targetDepartment.getName());
                    if(subjectEqualCondition){
                        return true;
                    }
                }
            }
            return false;
        }
        else{
            throw new ResourceNotFoundException("targetDepartment is null inside parameter in ComputeDepartment.");
        }
    }

}
