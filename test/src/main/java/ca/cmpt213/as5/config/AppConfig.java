package ca.cmpt213.as5.config;

import ca.cmpt213.as5.Organize_Data.SetCourse;
import ca.cmpt213.as5.Organize_Data.SetDepartmentIds;
import ca.cmpt213.as5.controller.CourseController;
import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.services.DepartmentService;
import ca.cmpt213.as5.watchers.Watcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * It is essentially a Java configuration class
 * that defines the application context and specifies
 * the relationships between different beans. This
 * allows for more fine-grained control over the
 * instantiation and configuration of objects in
 * the application, and makes it easier to manage
 * dependencies between different components.
 */

@Configuration
public class AppConfig {

    @Bean
    public List<CoursePlanner> courseList() throws ResourceNotFoundException, IOException, CourseNotFoundException {
        CourseController cc = new CourseController();
        return cc.courseList;
    }

    @Bean
    public DepartmentService departmentService(List<CoursePlanner> courseList) {
        return new DepartmentService(courseList);
    }

    @Bean
    public Watcher courseWatcher() {
        return new Watcher();
    }

    @Bean
    public SetCourse setCourse(List<CoursePlanner> courseList) {
        return new SetCourse(courseList);
    }

//    @Bean
//    public SetCourse setCourseUnique(List<CoursePlanner> courseList) {
//        return new SetCourseUnique(courseList);
//    }

    @Bean
    public SetDepartmentIds setDepartmentIds(List<CoursePlanner> courseList) throws IOException {
        return new SetDepartmentIds(courseList);
    }

}


