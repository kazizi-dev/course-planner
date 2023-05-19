package ca.project.service;

import ca.project.exception.DepartmentNotFoundException;
import ca.project.model.bean.Department;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DepartmentService {

    public DepartmentService(){}

    public Department getDepartmentById(List<Department> departments, int depId) throws DepartmentNotFoundException {
        for (Department department : departments) {
            if (department.getDeptId() == depId) {
                return department;
            }
        }
        throw new DepartmentNotFoundException();
    }
}
