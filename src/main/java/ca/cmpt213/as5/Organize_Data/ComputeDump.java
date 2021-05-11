package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/*
    This class is used to compute dump output.
 */
@JsonPropertyOrder({ "DEPT", "COURSE", "OFFERING"})
public class ComputeDump {
    private int DEPT;
    private int COURSE;
//    private int OFFERING;


    public ComputeDump(){

    }

    public ComputeDump(List<CoursePlanner> courseList) throws ResourceNotFoundException, CourseNotFoundException {
        ComputeDepartment computeDepartmentObj = new ComputeDepartment(courseList);
        computeDepartmentObj.addAllSubjectsToList();

        DEPT = computeDepartmentObj.getAllDepartmentsListSorted().size();

        for(Department departmentObj : computeDepartmentObj.getAllDepartmentsListSorted()){
            ComputeCatalog computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
            computeCatalogObj.addAllCatalogNumsToList();
            COURSE += computeCatalogObj.getSameSubjectCourseList().size();
        }
    }


    public int getDEPT() {
        return DEPT;
    }

    public void setDEPT(int DEPT) {
        this.DEPT = DEPT;
    }

    public int getCOURSE() {
        return COURSE;
    }

    public void setCOURSE(int COURSE) {
        this.COURSE = COURSE;
    }
}
