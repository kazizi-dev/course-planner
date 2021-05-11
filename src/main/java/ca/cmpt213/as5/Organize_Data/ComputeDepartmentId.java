package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.model.Department;

import java.util.ArrayList;
import java.util.List;

/*
    This class assigns id to each department.
 */
public class ComputeDepartmentId {
    private final static int FIVE = 5;
    private List<Department> departmentList = new ArrayList<>();

    public ComputeDepartmentId(List<Department> subjectsList){
        this.departmentList = subjectsList;

    }

    public List<Department> getSubjectListWithId(){
        return departmentList;
    }

    public void assignIdToSubject(){
        int count = 1;
        for(Department department : departmentList){
            department.setDeptId(count);
            count++;
        }
    }


}
