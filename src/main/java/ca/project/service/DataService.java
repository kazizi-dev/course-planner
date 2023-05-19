package ca.project.service;

import ca.project.exception.ResourceNotFoundException;
import ca.project.model.bean.*;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This class reads CSV files from a relative path, and it
 * will split each row of the CSV into appropriate data fields.
 * The information is stored in a list and can be accessed later.
 */

@Service
public class DataService {
    private int courseId;
    private int offeringId;
    private List<Department> departments;

    private final List<String> sentenceLines;
    private final String CSV_FILE_PATH = "data/course_data_2016.csv";
    private static final int SEMESTER_INDEX = 0;
    private static final int DEPARTMENT_INDEX = 1;
    private static final int CATALOG_NUM_INDEX = 2;
    private static final int LOCATION_INDEX = 3;
    private static final int ENROLMENT_CAP_INDEX = 4;
    private static final int ENROLMENT_TOTAL_INDEX = 5;
    private static final int INSTRUCTOR_INDEX = 6;
    private static final String STRINGS_TO_GET_RID_OF = ", [^\"]\"$#@&%~*/^!?";


    public DataService() throws ResourceNotFoundException, IOException {
        if (!CSV_FILE_PATH.endsWith(".csv")) {
            throw new ResourceNotFoundException("No .csv file found!");
        }

        this.courseId = 0;
        this.offeringId = 0;
        this.sentenceLines = new ArrayList<>();
        this.departments = new LinkedList<>();

        readFile();
    }


    public List<String> getDataAsList(){
        return sentenceLines;
    }

    public List<Department> getDepartments(){
        return this.departments;
    }

    public void initializeDepartments(){
        final String COLUMNS = "SEMESTER,SUBJECT";

        for(String sentenceLine : sentenceLines){
            if(!sentenceLine.contains(COLUMNS)){
                parseContentFromSentences(sentenceLine);
            }
        }
    }

    private void readFile() throws IOException, ResourceNotFoundException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String sentenceLine;
            while ((sentenceLine = bufferedReader.readLine()) != null) {
                String SPLIT_BY_COMMA = ",";
                sentenceLine = Arrays.toString(sentenceLine.split(SPLIT_BY_COMMA));
                String cleanLine = getRidOfUselessStrings(sentenceLine);
                sentenceLines.add(cleanLine);
            }
        }
    }


    // .............. Helper functions ................ //
    private void parseContentFromSentences(String sentenceLine){
        sentenceLine.replaceAll(STRINGS_TO_GET_RID_OF, "");
        String[] fields = sentenceLine.split(",");

        int semesterCode = Integer.parseInt(fields[SEMESTER_INDEX].trim());
        String catalogNumber = fields[CATALOG_NUM_INDEX].trim();
        String location = fields[LOCATION_INDEX].trim();

        Instructor instructorObj = new Instructor();
        instructorObj.initializeInstructors(fields, INSTRUCTOR_INDEX, fields.length - 2);

        Section sectionObj = new Section();
        sectionObj.setEnrollmentCap(Integer.parseInt(fields[ENROLMENT_CAP_INDEX].trim()));
        sectionObj.setEnrollmentTotal(Integer.parseInt(fields[ENROLMENT_TOTAL_INDEX].trim()));
        sectionObj.setType(fields[fields.length - 1].trim());

        Offering offering = new Offering();
        offering.setLocation(location);
        offering.addInstructorToList(instructorObj);
        offering.addSectionToList(sectionObj);
        offering.setSemesterCode(semesterCode);
        offering.setCourseOfferingId(offeringId);
        offeringId++;

        Course course = new Course();
        course.setCatalogNumber(catalogNumber);
        course.addToOfferingList(offering);
        course.setCourseId(courseId);
        courseId++;

        Department department = new Department();
        department.setName(fields[DEPARTMENT_INDEX].trim());

        Department duplicateDepartment = getDuplicateDepartment(department);

        // department already exists
        if(duplicateDepartment != null){
            // group courses from the same department
            duplicateDepartment.addCourseToList(course);
        }
        else {
            department.setDeptId(departments.size());
            departments.add(department);
        }

        Collections.sort(departments);
        for(Department d : departments){
            Collections.sort(d.getCourses());
            for(Course c : d.getCourses()){
                Collections.sort(c.getOfferings());
            }
        }
    }

    private Department getDuplicateDepartment(Department newDepartment){
        for(Department department : departments){
            if(department.getName().equals(newDepartment.getName())){
                return department;
            }
        }
        return null;
    }

    private String getRidOfUselessStrings(String targetLine){
        return targetLine
                .replace("[", "").replace("]", "")
                .replace("(","").replace(")","")
                .replace("$","").replace(".","")
                .replace("%","").replace("@","")
                .replace("#","").replace("*","")
                .replace("/","").replace("\\", "")
                .replace(":","").replace("'","")
                .replace("?","").replace("^","")
                .replace("+","").replace("&","")
                .replace("~","").replace("{","")
                .replace("}","").replace("=","")
                .replace("!","").replace("_","")
                .replace("|","").replace("`","")
                .replace("<","").replace(">","")
                .replace("]","").replace("[","")
                .replaceFirst(" ","").replaceFirst(" ","")
                .replaceFirst(" ","").replaceFirst(" ","")
                .replaceFirst(" ","").replaceFirst(" ","");
    }

    // debugging
    public void printSentenceList(){
        for(String sentence : sentenceLines){
            System.out.println(sentence);
        }
        System.out.println("*** End of testing ***");
    }
}
