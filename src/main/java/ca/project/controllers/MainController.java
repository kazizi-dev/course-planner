package ca.project.controllers;

import ca.project.exception.CourseNotFoundException;
import ca.project.exception.DepartmentNotFoundException;
import ca.project.exception.OfferingNotFoundException;
import ca.project.model.bean.*;
import ca.project.service.CourseService;
import ca.project.service.DataService;
import ca.project.service.DepartmentService;
import ca.project.service.OfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping(path = "/api")
public class
MainController {
    @Autowired
    private DataService dataService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private OfferingService offeringService;


    public MainController(DataService dataService, CourseService courseService, DepartmentService departmentService){
        List<String> sentenceLines = dataService.getDataAsList();
        dataService.initializeDepartments();
    }

    @RequestMapping(path="/about", method = RequestMethod.GET, produces = "application/json")
    public About getAbout(){
        return new About();
    }

    @RequestMapping(path="/courses", method = RequestMethod.GET, produces = "application/json")
    public List<Course> getCourses(){
        return courseService.getEveryDepartmentCourse(dataService.getDepartments());
    }

    @RequestMapping(path="/departments", method = RequestMethod.GET)
    public List<Department> getDepartments(){
        return dataService.getDepartments();
    }

    @RequestMapping(path="/departments/{deptId}/courses", method = RequestMethod.GET, produces = "application/json")
    public List<Course> getDepartmentCourses(@PathVariable("deptId") int deptId) throws DepartmentNotFoundException {
        Department department = departmentService.getDepartmentById(dataService.getDepartments(), deptId);
        return department.getCourses();
    }

    @RequestMapping(path="/departments/{deptId}/courses/{courseId}/offerings", method = RequestMethod.GET, produces = "application/json")
    public List<Offering> getOfferings(@PathVariable("deptId") int deptId,
                                       @PathVariable("courseId") int courseId) throws DepartmentNotFoundException, CourseNotFoundException {

        Department department = departmentService.getDepartmentById(dataService.getDepartments(), deptId);
        Course course = courseService.getCourseById(department.getCourses(), courseId);

        return course.getOfferings();
    }

    @RequestMapping(path="/departments/{deptId}/courses/{courseId}/offerings/{offeringId}", method = RequestMethod.GET, produces = "application/json")
    public List<Section> getSections(@PathVariable("deptId") int deptId,
                                     @PathVariable("courseId") int courseId,
                                     @PathVariable("offeringId") int offeringId) throws CourseNotFoundException, OfferingNotFoundException, DepartmentNotFoundException {

        Department department = departmentService.getDepartmentById(dataService.getDepartments(), deptId);
        Course course = courseService.getCourseById(department.getCourses(), courseId);
        Offering offering = offeringService.getOfferingById(course.getOfferings(), offeringId);

        return offering.getSections();
    }

    @RequestMapping(path="/stats/students-per-semester", method = RequestMethod.GET, produces = "application/json")
    public List<Graph> getSections(@RequestParam int deptId) throws DepartmentNotFoundException {
        Department department = departmentService.getDepartmentById(dataService.getDepartments(), deptId);

        return offeringService.getGraphs(department, department.getFirstSemesterCode(), department.getLastSemesterCode());
    }
}
