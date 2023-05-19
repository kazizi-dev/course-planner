package ca.project.service;

import ca.project.exception.CourseNotFoundException;
import ca.project.exception.OfferingNotFoundException;
import ca.project.model.bean.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {
    public CourseService() {}

    public List<Course> getEveryDepartmentCourse(List<Department> departments){
        List<Course> courses = new LinkedList<>();
        for(Department department : departments){
            if(department.getCourses().size() > 0){
                courses.addAll(department.getCourses());
            }
        }
        return courses;
    }

    public Course getCourseById(List<Course> courses, int courseId) throws CourseNotFoundException {
        for(Course course : courses){
            if(course.getCourseId() == courseId){
                return course;
            }
        }
        throw new CourseNotFoundException();
    }
}
