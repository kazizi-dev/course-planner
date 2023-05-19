package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.model.Courses.CoursePlanner;

import java.util.List;

/*
    This class assigns each course an Id.
 */
public class ComputeCourseId {
    private List<CoursePlanner> departmentCourseList;

    public ComputeCourseId(List<CoursePlanner> departmentCourseList){
        this.departmentCourseList = departmentCourseList;
    }

    public List<CoursePlanner> getSubjectListWithId(){
        return departmentCourseList;
    }

    public void assignIdToSubject(){
        int count = 1;
        for(CoursePlanner coursePlanner : departmentCourseList){
            coursePlanner.setCourseId(count);
            count++;
        }
    }

}
