package ca.cmpt213.as5.output;

import ca.cmpt213.as5.Organize_Data.*;
import ca.cmpt213.as5.exception.CourseNotFoundException;
import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.model.CatalogNumber;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
    Print class is responsible for printing the information
    of each course such as Department, Catalog, instructors, etc.
    It is also responsible for writing the information that is
    printed inside a text file stored in data directory.
 */
public class Print {
    private static final String NEW_LINE = "\n";
    private static final String WRITE_PATH_TXT_FILE = "./data/KouroshAziziOutput.txt";

    private List<CoursePlanner> courseList = new ArrayList<>();
    private FileWriter writer;
    private ComputeDepartment computeDepartmentObj;
    private ComputeDepartmentId computeDepartmentId;

    public Print(List<CoursePlanner> courseList) throws IOException {
        this.courseList = courseList;
        writer = new FileWriter(new File(WRITE_PATH_TXT_FILE), true);
        computeDepartmentObj = new ComputeDepartment(courseList);
    }

    public void printCleanOutput() throws CourseNotFoundException, ResourceNotFoundException {
        try {
            computeDepartmentObj.addAllSubjectsToList();

            for (Department departmentObj : computeDepartmentObj.getAllDepartmentsListSorted()) {

                ComputeCatalog computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
                computeCatalogObj.addAllCatalogNumsToList();

                for (CatalogNumber catalogObj : computeCatalogObj.getCatalogNumList()) {
                    // print upper body of text
                    printSubjectAndCatalog(departmentObj, catalogObj);
                    writeSubjectAndCatalog(departmentObj, catalogObj);
                    mergeSameCourses(computeCatalogObj.getSameSubjectCourseList(), departmentObj, catalogObj);
                }
            }
        }
        catch (IOException e) {
            System.out.println("IOException in Print class (1)");
        }
    }

    private void mergeSameCourses(List<CoursePlanner> sameSubjectCourseList, Department departmentObj,
                                  CatalogNumber catalogNumber) throws CourseNotFoundException, IOException {

        MergeCourses mergeObj = new MergeCourses(sameSubjectCourseList, departmentObj,
                catalogNumber.getCatalogNum());

        mergeObj.mergeSameCourses();

        for (CoursePlanner course : mergeObj.getAlreadyChosenCourseList(mergeObj.getSameCourseListSorted(), catalogNumber)){
            // print lower body of text
            printSemesterAndInstructors(course);
            writeSemesterAndInstructors(course);
            printComponentAndEnrolments(course);
            writeComponentAndEnrolments(course);
        }
    }

    private void printSemesterAndInstructors(CoursePlanner course) throws IOException {
        System.out.println("\t\t" + course.getSemester().getSemesterNum() + " "
                + " in " + course.getLocation().getCampusLocName() + " by "
                + course.getInstructor().getAllInstructorsStr());
    }

    private void writeSemesterAndInstructors(CoursePlanner course) throws IOException {
        append("\t\t" +  course.getSemester().getSemesterNum() + " " + " in " +
                course.getLocation().getCampusLocName() + " by "
                + course.getInstructor().getFullName());
        append(String.valueOf(course.getInstructor().getAllInstructors()));
        append(NEW_LINE);
    }

    private void printSubjectAndCatalog(Department departmentObj, CatalogNumber catalogObj) throws IOException {
        System.out.print(departmentObj.getName().toUpperCase());
        System.out.println(" " + catalogObj.getCatalogNum());
    }

    private void writeSubjectAndCatalog(Department departmentObj, CatalogNumber catalogObj) throws IOException {
        append(departmentObj.getName().toUpperCase());
    }

    private void printComponentAndEnrolments(CoursePlanner course) throws IOException {
        System.out.println("\t\t\t Type = " + course.getComponent().getComponentCode().toUpperCase()
                + ", Enrollment = " + course.getEnrolmentTotalObj().getEnrolmentTotalNum() + "/" +
                course.getEnrolMentCapObj().getEnrolmentCap());
    }

    private void writeComponentAndEnrolments(CoursePlanner course) throws IOException {
        append("\t\t\t Type = " + course.getComponent().getComponentCode().toUpperCase()
                + ", Enrollment = " + course.getEnrolmentTotalObj().getEnrolmentTotalNum()
                + "/" + course.getEnrolMentCapObj().getEnrolmentCap());
        append(NEW_LINE);
    }

    private void append(String str) throws IOException {
        for(int i = 0; i < str.length(); i++){
            writer.append(str.charAt(i));
        }
    }

    // .................... GET /api/departments .................... //
    public void printListOfDepartments() throws CourseNotFoundException, ResourceNotFoundException {
        computeDepartmentObj.addAllSubjectsToList();
        ComputeDepartmentId computeSubjIdObj = new ComputeDepartmentId(computeDepartmentObj.getAllDepartmentsListSorted());
        computeSubjIdObj.assignIdToSubject();
        System.out.println(">> List of all departments: ");
        for(Department departmentObj : computeSubjIdObj.getSubjectListWithId()){
            System.out.println(departmentObj.getName() + " and Id: " + departmentObj.getDeptId());
        }
    }

    public void printListOfDepartmentCourses(int departmentCourseId) throws CourseNotFoundException {
        computeDepartmentObj.getAllDepartmentsListSorted();
        ComputeCatalog computeCatalogObj;
        for (Department departmentObj : computeDepartmentObj.getAllDepartmentsListSorted()){
            if(departmentObj.getDeptId() == (departmentCourseId)){
                computeCatalogObj = new ComputeCatalog(courseList, departmentObj);
                computeCatalogObj.addAllCatalogNumsToList();
                for(CoursePlanner courseObj : computeCatalogObj.getSameSubjectCourseList()){
                    System.out.println(courseObj);  // debugging
                }
            }
        }
    }

}
