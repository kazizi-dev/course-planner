package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;

import java.io.IOException;
import java.util.List;

public class SetDepartmentIds {

    private List<CoursePlanner> courseList;
    private ComputeDepartment computeDepartmentObj;

    public SetDepartmentIds(List<CoursePlanner> courseList) throws IOException {
        this.courseList = courseList;
        computeDepartmentObj = new ComputeDepartment(courseList);
    }

    public void setDepartmentId() throws ResourceNotFoundException, CourseNotFoundException {
        computeDepartmentObj.addAllSubjectsToList();
        ComputeDepartmentId computeSubjIdObj = new ComputeDepartmentId(computeDepartmentObj.getAllDepartmentsListSorted());
        computeSubjIdObj.assignIdToSubject();
    }

    public void setDepartmentCourseId() throws CourseNotFoundException, ResourceNotFoundException {
        ComputeCatalog computeCatalogObj;
        for(Department departmentObj : computeDepartmentObj.getAllDepartmentsListSorted()){
            computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
            computeCatalogObj.addAllCatalogNumsToList();
            ComputeCourseId computeCourseId = new ComputeCourseId(computeCatalogObj.getSameSubjectCourseList());
            computeCourseId.assignIdToSubject();
        }
    }

}
