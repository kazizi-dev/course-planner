package ca.cmpt213.as5.services;

import ca.cmpt213.as5.model.Department;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The DepartmentService takes a list of CoursePlanner
 * objects and creates a list of Department objects based
 * on the department names contained in the courses. It
 * provides methods to retrieve a list of all departments
 * and a specific department by its ID.
 */

@Service
public class DepartmentService {
    private List<Department> departmentList = new ArrayList<>();

    public DepartmentService(List<CoursePlanner> courseList) {
        createDepartmentList(courseList);
    }

    public List<Department> getAllDepartments() {
        return departmentList;
    }

    public Department getDepartmentById(int deptId) {
        for (Department department : departmentList) {
            if (department.getDeptId() == deptId) {
                return department;
            }
        }
        return null;
    }

    private void createDepartmentList(List<CoursePlanner> courseList) {
        for (CoursePlanner course : courseList) {
            int deptId = (int) course.getDepartment().getDeptId();
            Department department = getDepartmentById(deptId);
            if (department == null) {
                department = new Department(course.getDepartment().getName());
                department.setDeptId(deptId);
                departmentList.add(department);
            }
        }
        Collections.sort(departmentList);
    }
}
