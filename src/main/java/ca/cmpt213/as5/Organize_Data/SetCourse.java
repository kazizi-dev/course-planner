package ca.cmpt213.as5.Organize_Data;

import ca.cmpt213.as5.model.*;
import ca.cmpt213.as5.model.Courses.CoursePlanner;

import java.util.ArrayList;
import java.util.List;

/*
    This class receives the information of each line
    from the CsvReader class, and it sets the right
    information in the right class. It allows the user
    to access the list of classes to access the instances
    of each class within that class.
 */
public class SetCourse {
    private static final int SEMESTER_INDEX = 0;
    private static final int SUBJECT_INDEX = 1;
    private static final int CATALOG_NUM_INDEX = 2;
    private static final int LOCATION_INDEX = 3;
    private static final int ENROLMENT_CAP_INDEX = 4;
    private static final int ENROLMENT_TOTAL_INDEX = 5;
    private static final int INSTRUCTOR_INDEX = 6;
    private static final int COMPONENT_CODE_INDEX = 7;
    private static final String STRINGS_TO_GET_RID_OF = ", [^\"]\"$#@&%~*/^!?";
    private List<CoursePlanner> courseList = new ArrayList<>();
    public SetCourse(){

    }

    public void setInformation(List<String> sentenceList){
        if(!sentenceList.isEmpty()){
            parseSentenceList(sentenceList);
        }
    }

    private void parseSentenceList(List<String> sentenceList) {
        for(String sentenceLine : sentenceList) {
            if(sentenceLine.contains("SEMESTER,SUBJECT")){
                continue;
            }

            CoursePlanner coursePlanner = new CoursePlanner();
            makeCourse(sentenceLine, coursePlanner);

            boolean shouldSkip = false;
            for (CoursePlanner planner : courseList) {
                if(planner.equals(coursePlanner)){
                    shouldSkip = true;
                    break;
                }
            }
            if(!shouldSkip) {
                courseList.add(coursePlanner);
            }
        }
    }

    private void makeCourse(String sentenceLine, CoursePlanner coursePlanner) {
        sentenceLine.replaceAll(STRINGS_TO_GET_RID_OF, "");
        String[] separatedWords = (
                sentenceLine.split(","));

        Semester semester = new Semester(separatedWords[SEMESTER_INDEX].trim());
        coursePlanner.setSemester(semester);

        Department department = new Department(separatedWords[SUBJECT_INDEX].trim());
        coursePlanner.setDepartment(department);

        CatalogNumber catalogNumber = new CatalogNumber(separatedWords[CATALOG_NUM_INDEX].trim());
        coursePlanner.setCatalog(catalogNumber);

        Location location = new Location(separatedWords[LOCATION_INDEX].trim());
        coursePlanner.setLocation(location);

        separatedWords[ENROLMENT_CAP_INDEX] = separatedWords[ENROLMENT_CAP_INDEX].trim();
        EnrolmentCapacity enrolmentCapacity =
                new EnrolmentCapacity(Integer.parseInt(separatedWords[ENROLMENT_CAP_INDEX].trim()));
        coursePlanner.setEnrolmentCapacity(enrolmentCapacity);

        separatedWords[ENROLMENT_TOTAL_INDEX] = separatedWords[ENROLMENT_TOTAL_INDEX].trim();
        EnrolmentTotal enrolmentTotal =
                new EnrolmentTotal(Integer.parseInt(separatedWords[ENROLMENT_TOTAL_INDEX].trim()));
        coursePlanner.setEnrolmentTotal(enrolmentTotal);

        Instructor instructor = new Instructor(separatedWords, INSTRUCTOR_INDEX, separatedWords.length - 2);
        coursePlanner.setInstructor(instructor);

        ComponentCode componentCode = new ComponentCode(separatedWords[separatedWords.length - 1].trim());
        coursePlanner.setComponent(componentCode);
    }

    public List<CoursePlanner> getCourseList(){
        return courseList;
    }

}
