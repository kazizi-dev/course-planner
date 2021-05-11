package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.model.CatalogNumber;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

/*
    This class stores the information about the list of all
    classes, the list of all catalog numbers, and the list of
    subjects that are the same.
 */
public class ComputeCatalog {
    private List<CoursePlanner> courseList = new ArrayList<>();
    private List<CatalogNumber> catalogNumList = new ArrayList<>();
    private List<CoursePlanner> sameSubjectCourseList = new ArrayList<>();
    private Department department = new Department();

    public ComputeCatalog(){

    }

    public ComputeCatalog(List<CoursePlanner> courseList, Department department){
        this.courseList = courseList;
        this.department = department;
    }

    public List<CoursePlanner> getSameSubjectCourseList(){
        return sameSubjectCourseList;
    }

    public List<CatalogNumber> getCatalogNumList(){
        return catalogNumList;
    }

    public void setCourseList(List<CoursePlanner> courseList){
        this.courseList = courseList;
    }

    public void setDepartment(Department department){
        this.department = department;
    }

    public void addAllCatalogNumsToList() throws CourseNotFoundException {
        if(!courseList.isEmpty()){
            for(CoursePlanner course : courseList){
                checkIfSubjectsEqual(course);
            }
        }
        else{
            throw new CourseNotFoundException("Course list is empty; no course found");
        }
    }

    // ................ Helper functions ................. //
    private void checkIfSubjectsEqual(CoursePlanner course) throws CourseNotFoundException {
        if(course != null){
            boolean courseSubjectCondition = course.getDepartment().getName().equals(department.getName());
            if(courseSubjectCondition){
                sameSubjectCourseList.add(course);
                checkCatalogNumber(course);
            }
        }
        else{
            throw new CourseNotFoundException("Course not found exception.");
        }
    }

    private void checkCatalogNumber(CoursePlanner course) throws CourseNotFoundException {
        if(course != null) {
            boolean catalogCondition = isCatalogNumberEqual(course.getCatalog());
            if (!catalogCondition) {
                catalogNumList.add(course.getCatalog());
            }
        }
        else{
            throw new CourseNotFoundException("Course not found exception.");
        }
    }

    private boolean isCatalogNumberEqual(CatalogNumber catalogNumber){
        if(catalogNumber != null){
            for(CatalogNumber catalogNum : catalogNumList){
                boolean sameCatalogNumCondition = catalogNum.getCatalogNum().equals(catalogNumber.getCatalogNum());
                if(sameCatalogNumCondition){
                    return true;
                }
            }
            return false;
        }
        else{
            throw new ResourceAccessException("Catalog number is not found.");
        }
    }

}
