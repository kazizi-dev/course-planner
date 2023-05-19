package ca.cmpt213.as5.controller;

import ca.cmpt213.as5.Organize_Data.*;
import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.InvalidIndexException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.Organize_Data.SetDepartmentIds;
import ca.cmpt213.as5.model.Courses.*;
import ca.cmpt213.as5.output.Print;
import ca.cmpt213.as5.reader.Reader;
import ca.cmpt213.as5.services.DepartmentService;
import ca.cmpt213.as5.watchers.Watcher;
import ca.cmpt213.as5.watchers.WatcherInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
    This class interacts with Spring Boot through
    the use of POST and GET. It is also responsible
    for communicating with other classes based on the
    user input choice on MobaXterm program.
 */

@RestController
public class CourseController {
    private List<Watcher> watchers = new ArrayList<>();
    public List<CoursePlanner> courseList = new ArrayList<>();
    private static final String GREETING_MESSAGE = "Course planner written by Kourosh Azizi and Quince Bielka for part 2.";
    private static final String CSV_FILE_PATH = "data/course_data_2016.csv";
    private Reader readerObj;
    private SetCourse setCourseObj;
    private SetCourse setCourseUniqueObj;
    private SetDepartmentIds setDepartmentIdsObj;


    public CourseController(DepartmentService ds){
        this.departmentService = ds;
    }


    @Autowired
    private DepartmentService departmentService;

    private List<Department> departmentList = new ArrayList<>();

    public CourseController(){
        try {
            readCSV();
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        }
    }


    @ResponseStatus(code = HttpStatus.OK, reason = "OK")
    @RequestMapping(path="/api/setLocation", method = RequestMethod.POST)
    public void setLocation(@RequestBody Location location){
        System.out.println("Location: " + location.getCampusLocName());
    }


    @RequestMapping(path="/api/about", method = RequestMethod.GET, produces = "application/json")
    public About getAboutMessage() throws ResourceNotFoundException, CourseNotFoundException, IOException {
        return new About();
    }


    @RequestMapping(path="/api/dump-model", method = RequestMethod.GET, produces = "application/json")
    public ComputeDump getDumpOutput() throws ResourceNotFoundException, IOException, CourseNotFoundException {
        readCSV();
        Print print = new Print(courseList);
        print.printCleanOutput();
        return new ComputeDump(courseList);
    }


    @RequestMapping(path="/api/departments", method = RequestMethod.GET, produces = "application/json")
    public List<Department> getListOfDepartments(){
        return departmentService.getAllDepartments();
    }


    @RequestMapping(path="/api/departments/{deptId}/courses", method = RequestMethod.GET, produces = "application/json")
    public List<ComputeCourseOutput> getCoursesInDepartment(@PathVariable ("deptId") int departmentId)
            throws ResourceNotFoundException, IOException, CourseNotFoundException {

        List<ComputeCourseOutput> courses = new ArrayList<>();
        ComputeDepartment computeDepartment = new ComputeDepartment(courseList);
        computeDepartment.addAllSubjectsToList();
        for(Department department : getListOfDepartments()){
            if(department.getDeptId() == departmentId){
                ComputeCatalog computeCatalogObj = new ComputeCatalog(courseList, department);
                computeCatalogObj.addAllCatalogNumsToList();
                for(CoursePlanner coursePlannerObj : computeCatalogObj.getSameSubjectCourseList()) {
                    courses.add(new ComputeCourseOutput(coursePlannerObj));
                }
            }
        }

        Collections.sort(courses);
//        List<CoursePlanner> courses = departmentService.getAllCoursesForDepartment(departmentId);
        
        return courses;
    }


    @RequestMapping(path="/api/departments/{deptId}/courses/{courseId}/offerings", method = RequestMethod.GET, produces = "application/json")
    public List<CourseOffering> getListOfOfferings(@PathVariable ("courseId") long courseId, @PathVariable ("deptId") int depId ) throws CourseNotFoundException, IOException, ResourceNotFoundException, InvalidIndexException {
        List<CourseOffering> listOfOfferings = getCourseOfferings(courseId, depId);
        return listOfOfferings;
    }


    @RequestMapping(path="/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}", method = RequestMethod.GET, produces = "application/json")
    public List<CourseEnrollmentData> getSpecificOffering(@PathVariable ("deptId") int depId,
                                              @PathVariable ("offeringId") int courseOfferingId,
                                              @PathVariable ("courseId") long courseId )
            throws CourseNotFoundException, IOException, ResourceNotFoundException, InvalidIndexException {
        List<CourseOffering> courses = getCourseOfferings(courseId, depId);
        for (CourseOffering course : courses) {
            if(course.getCourseOfferingId().intValue() == (courseOfferingId)){
                CourseEnrollmentData data = new CourseEnrollmentData(course);
                System.out.println(data.getEnrollmentTotal());
                System.out.println(data.getEnrollmentCap());
                System.out.println(course.getCourseOfferingId());
                List<CourseEnrollmentData> offerings = new ArrayList<>();
                offerings.add(data);
                return offerings;
            }
        }
        throw new InvalidIndexException();
    }


    private List<CourseOffering> getCourseOfferings
            (long courseId, int depId)
            throws ResourceNotFoundException, IOException, CourseNotFoundException, InvalidIndexException {
        List<CoursePlanner> coursesInOneDepartment =
                new ComputeDepartment().
                        getListOfDepartmentCourseOfferings(courseList, depId);
        List<CourseOffering> courseOfferings =
                (new ComputeDepartment()
                        .getListOfDepartmentCoursesOfType
                                (coursesInOneDepartment, courseId));
        return courseOfferings;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/api/addoffering", method = RequestMethod.POST)
    public void addOffering(@RequestBody CourseInput planner)
            throws CourseNotFoundException, IOException, ResourceNotFoundException {

        CoursePlanner course = new CoursePlanner(
                planner.getSemester()+"", planner.getSubjectName(), planner.getCatalogNumber(),
                planner.getLocation(), planner.getEnrollmentCap(), planner.getComponent(),
                planner.getEnrollmentTotal(), planner.getInstructor());
        courseList.add(course);
        SetDepartmentIds depIdSetter = new SetDepartmentIds(courseList);
        depIdSetter.setDepartmentId();
        depIdSetter.setDepartmentCourseId();
        for (Watcher watcher : watchers) {
            if(watcher.getDepartment().equals(course.getDepartment())){
                watcher.update(course);
                break;
            }
        }
    }

    private final static int SPRING_SEMESTER_CODE = 1;
    private final static int SUMMER_SEMESTER_CODE = 4;
    private final static int FALL_SEMESTER_CODE = 7;

    private boolean isValidSemesterCode(int code) {
        // semester codes are the last digit of course codes
        int semesterCode = code % 10;

        return semesterCode == SPRING_SEMESTER_CODE
                || semesterCode == SUMMER_SEMESTER_CODE
                || semesterCode == FALL_SEMESTER_CODE;
    }


    @RequestMapping(path="/api/stats/students-per-semester", method = RequestMethod.GET, produces = "application/json")
    public List<GraphData> giveGraphDataPoints(@RequestParam int depId) throws CourseNotFoundException, IOException, ResourceNotFoundException, InvalidIndexException {
        List<CoursePlanner> targetList = new ArrayList<>();
        for(CoursePlanner course : courseList){
            if(course.getDepartment().getDeptId() == depId){
                targetList.add(course);
            }
        }

        List<GraphData> graphDataPoints = getGraphData(targetList);
        return graphDataPoints;
    }


    private List<GraphData> getGraphData(List<CoursePlanner> coursesInOneDepartment) throws InvalidIndexException, CourseNotFoundException, IOException, ResourceNotFoundException {
        List<GraphData> graphDataPoints = new ArrayList<>();
        String sCode = "";
        int index = 0;
        for (CoursePlanner planner : coursesInOneDepartment) {
            if(graphDataPoints.isEmpty()){
                int semesterNum = planner.getSemesterNum();
                int courseId = planner.getCourseId();
                int departmentId = (int) planner.getDepartment().getDeptId();
                graphDataPoints.add(new GraphData(semesterNum, getCourseOfferings(courseId, departmentId)));
                sCode = planner.getSemesterNum()+"";
                continue;
            }

            if(planner.getSemester().getSemesterNum().equalsIgnoreCase(sCode)){
                graphDataPoints.get(index).setTotalCoursesTaken(
                        graphDataPoints.get(index).getTotalCoursesTaken()
                                + planner.getEnrolmentTotalObj().getEnrolmentTotalNum()
                );
            }else {
                index++;
                int semesterNum = planner.getSemesterNum();
                int courseId = planner.getCourseId();
                int departmentId = (int) planner.getDepartment().getDeptId();
                graphDataPoints.add(new GraphData(semesterNum, getCourseOfferings(courseId, departmentId)));
                sCode = planner.getSemesterNum()+"";
            }
        }
        return graphDataPoints;
    }


    // ........................... Helper function .......................... //
    public void readCSV() throws ResourceNotFoundException, IOException, CourseNotFoundException {
        if(readerObj == null) {
            readerObj = new Reader(CSV_FILE_PATH);
//            csvReaderObj.printCsvSentenceList();       // print every line read from csv : kept for debugging
        }

        if(setCourseObj == null){
            setCourseObj = new SetCourse();
            setCourseObj.setInformation(readerObj.getSentenceList());
            courseList = setCourseObj.getCourseList();
            //computeMergedCourseList();
        }

        if(setDepartmentIdsObj == null){
            setDepartmentIdsObj = new SetDepartmentIds(courseList);
            setDepartmentIdsObj.setDepartmentId();
            setDepartmentIdsObj.setDepartmentCourseId();
        }
    }


    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "An index given was invalid")
    @ExceptionHandler(InvalidIndexException.class)
    public void invalidIndexHandler(){}


    @RequestMapping(path="/api/watchers", method = RequestMethod.GET, produces = "application/json")
    public List<Watcher> listAllWatchers(){
        return watchers;
    }


    @RequestMapping(path="/api/watchers", method = RequestMethod.POST)
    public Watcher addWatcher(@RequestBody WatcherInput ids) throws CourseNotFoundException, IOException, ResourceNotFoundException {
        List<CoursePlanner> coursesInOneDepartment =
                new ComputeDepartment().
                        getListOfDepartmentCourseOfferings(courseList, ids.getDeptId());
        List<CoursePlanner> coursesofType = new ArrayList<>();
        Watcher newWatcher = null;
        for (CoursePlanner course: coursesInOneDepartment) {
            if(course.getCourseId() == ids.getCourseId()){
                if(newWatcher == null) {
                    newWatcher = new Watcher(course);
                }
                course.addObserver(newWatcher);
            }
        }
        watchers.add(newWatcher);
        return newWatcher;
    }


    @RequestMapping(path="/api/watchers/{watcherId}", method = RequestMethod.GET, produces = "application/json")
    public Watcher getSpecificWatcher(@PathVariable ("watcherId") long watcherId){
        Watcher seek = new Watcher(watcherId);
        int index = watchers.indexOf(seek);
        return watchers.get(index);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path="/api/watchers/{watcherId}", method = RequestMethod.DELETE)
    public void deleteSpecificWatcher(@PathVariable ("watcherId") long watcherId){
        Watcher seek = new Watcher(watcherId);
        int index = watchers.indexOf(seek);
    }
}

