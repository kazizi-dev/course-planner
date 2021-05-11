package ca.cmpt213.as5.controller;

import ca.cmpt213.as5.Organize_Data.*;
import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.InvalidIndexException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.Organize_Data.SetDepartmentIds;
import ca.cmpt213.as5.model.Courses.*;
import ca.cmpt213.as5.output.Print;
import ca.cmpt213.as5.reader.CsvReader;
import ca.cmpt213.as5.watchers.Watcher;
import ca.cmpt213.as5.watchers.WatcherInput;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    private List<CoursePlanner> courseList = new ArrayList<>();
    private static final String GREETING_MESSAGE = "Course planner written by Kourosh Azizi and Quince Bielka for part 2.";
    private static final String CSV_FILE_PATH = "data/course_data_2018.csv";
    private CsvReader csvReaderObj;
    private SetCourse setCourseObj;
    private SetCourse setCourseUniqueObj;
    private SetDepartmentIds setDepartmentIdsObj;

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
    @GetMapping("/api/about")
    public AppAndAuthor getAboutMessage() throws ResourceNotFoundException, CourseNotFoundException, IOException {
        return new AppAndAuthor();
    }

    @GetMapping({"/api/dump-model"})
    public ComputeDump getDumpOutput() throws ResourceNotFoundException, IOException, CourseNotFoundException {
        readCSV();
        Print print = new Print(courseList);
        print.printCleanOutput();
        return new ComputeDump(courseList);
    }

    @GetMapping("/api/departments")
    public List<Department> getListOfDepartments()
            throws CourseNotFoundException, ResourceNotFoundException, IOException {

        ComputeDepartment computeDepartmentObj = new ComputeDepartment(courseList);
        computeDepartmentObj.addAllSubjectsToList();

        List<String> departmentsS = new ArrayList<>();
        List<Department> departmentsD = computeDepartmentObj.getAllDepartmentsListSorted();
        Department[] departmentsA = new Department[departmentsD.size()];
        for (int i = 0; i < departmentsD.size(); i++) {
            Department department = departmentsD.get(i);
            departmentsS.add((department.getName()));
            departmentsA[i] = department;
        }

        return departmentsD;
    }

    @GetMapping("/api/departments/{deptId}/courses")
    public List<ComputeCourseOutput> getCoursesInDepartment(@PathVariable ("deptId") int departmentId)
            throws ResourceNotFoundException, IOException, CourseNotFoundException {

        List<ComputeCourseOutput> coursesInDepartment =
                new ComputeDepartment().getListOfDepartmentCourses(courseList, departmentId);
        Collections.sort(coursesInDepartment);
        return coursesInDepartment;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings")
    public List<CourseOffering> getListOfOfferings(@PathVariable ("courseId") long courseId,
                                                   @PathVariable ("deptId") int depId )
            throws CourseNotFoundException, IOException, ResourceNotFoundException, InvalidIndexException {
        List<CourseOffering> listOfOfferings = getCourseOfferings(courseId, depId);
        return listOfOfferings;
    }

    @GetMapping("/api/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
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


    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping ("/api/stats/students-per-semester")
    public List<GraphData> giveGraphDataPoints(@RequestParam int depId) throws CourseNotFoundException, IOException, ResourceNotFoundException {
        List<CoursePlanner> coursesInOneDepartment =
                new ComputeDepartment().
                        getListOfDepartmentCourseOfferings(courseList, depId);
        class coursesSortBySemesterCode implements Comparator<CoursePlanner> {

            @Override
            public int compare(CoursePlanner o1, CoursePlanner o2) {
                return Integer.parseInt(o1.getSemester().getSemesterNum()) -
                        Integer.parseInt(o2.getSemester().getSemesterNum());
            }
        }
        Collections.sort(coursesInOneDepartment,new coursesSortBySemesterCode());
        List<GraphData> graphDataPoints = getGraphData(coursesInOneDepartment);
        return graphDataPoints;
    }

    private List<GraphData> getGraphData(List<CoursePlanner> coursesInOneDepartment) {
        List<GraphData> graphDataPoints = new ArrayList<>();
        String sCode = "";
        int index = 0;
        for (CoursePlanner planner : coursesInOneDepartment) {
            if(graphDataPoints.isEmpty()){
                graphDataPoints.add(
                        new GraphData(planner.getSemesterNum(),
                        planner.getEnrolmentTotalObj().getEnrolmentTotalNum()));
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
                graphDataPoints.add(
                        new GraphData(planner.getSemesterNum(),
                                planner.getEnrolmentTotalObj().getEnrolmentTotalNum()));
                sCode = planner.getSemesterNum()+"";
            }
        }
        return graphDataPoints;
    }

    // ........................... Helper function .......................... //
    private void readCSV() throws ResourceNotFoundException, IOException, CourseNotFoundException {
        if(csvReaderObj == null) {
            csvReaderObj = new CsvReader(CSV_FILE_PATH);
//            csvReaderObj.printCsvSentenceList();       // print every line read from csv : kept for debugging
        }

        if(setCourseObj == null){
            setCourseObj = new SetCourse();
            setCourseObj.setInformation(csvReaderObj.getSentenceList());
            courseList = setCourseObj.getCourseList();
            //computeMergedCourseList();
        }

        if(setDepartmentIdsObj == null){
            setDepartmentIdsObj = new SetDepartmentIds(courseList);
            setDepartmentIdsObj.setDepartmentId();
            setDepartmentIdsObj.setDepartmentCourseId();
        }

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "An index given was invalid")
    @ExceptionHandler(InvalidIndexException.class)
    public void invalidIndexHandler(){}



    @GetMapping("/api/watchers")
    public List<Watcher> listAllWatchers(){
        return watchers;
    }

    @PostMapping("/api/watchers")
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

    @GetMapping("/api/watchers/{watcherId}")
    public Watcher getSpecificWatcher(@PathVariable ("watcherId") long watcherId){
        Watcher seek = new Watcher(watcherId);
        int index = watchers.indexOf(seek);

        return watchers.get(index);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/watchers/{watcherId}")
    public void deleteSpecificWatcher(@PathVariable ("watcherId") long watcherId){
        Watcher seek = new Watcher(watcherId);
        int index = watchers.indexOf(seek);

    }


}

