package ca.cmpt213.as5.model;

import java.util.*;

/*
    This class stores the name of a professor that is teaching
    the course. It also allows multiple professors to added, and
    it does the necessary steps to ensure they are properly extracted.
 */
public class  Instructor {
    private String fullName;
    private List<String> instructorList = new ArrayList<>();
    private static final String SPLIT_BY_COMMA = ",";
    private static final String STRING_SIGN = "\"";
    private static final String STRING_NULL = "nu";
    private static final String EMPTY_STRING = "";

    public Instructor(){}

    public Instructor(String fullName) {
        if(isMultipleInstructor()){
            splitInstructorName();
        }
        else{
            this.fullName = fullName;
            instructorList.add(fullName);
        }
    }
    public Instructor(String[] separatedWords, int start, int end) {
        if(start != end){
            while(start <= end){
                if(start == end){
                    separatedWords[start] = separatedWords[start].substring(
                            0,separatedWords[start].length() - 1);
                }
                this.instructorList.add(separatedWords[start].trim());
                start++;
            }
            getRidOfDuplicateNames();
        }
        else{
            separatedWords[start] = separatedWords[start].substring(
                    0,separatedWords[start].length() - 2);
            this.fullName = separatedWords[start].trim();
            instructorList.add(fullName);
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAllInstructors(List<String> instructorList){
        this.instructorList = instructorList;
        getRidOfDuplicateNames();
    }

    public List<String> getAllInstructors(){
        return instructorList;
    }

    public String getFullName() {
        return fullName;
    }

    // ............... Helper Functions ................ //
    private Boolean isMultipleInstructor(){
        return instructorList.contains(SPLIT_BY_COMMA);
    }

    private void splitInstructorName(){
        for(String strProf : instructorList){
            String[] profs = strProf.split(SPLIT_BY_COMMA);
            this.instructorList.addAll(Arrays.asList(profs));
        }
        getRidOfDuplicateNames();
    }

    private void getRidOfDuplicateNames() {
        // HashSet will ignore duplicates and output names in order
        HashSet<String> hashSet = new HashSet<>(instructorList); // HashSet will ignore duplicates
        instructorList.clear();
        instructorList.addAll(hashSet);
    }

    private StringBuilder getRidOfNullProfs() {
        StringBuilder finalOutput = new StringBuilder();
        for(int i = 0; i < instructorList.size(); i++){
            String instructor = instructorList.get(i);
            if(!instructor.contains(STRING_NULL)){
                finalOutput.append(instructor);
                if(i < instructorList.size()-1){
                    finalOutput.append(SPLIT_BY_COMMA + " ");
                }
            }
        }
        return finalOutput;
    }

    public String getAllInstructorsStr() {
        return getRidOfNullProfs().toString().replaceAll(STRING_SIGN, EMPTY_STRING);
    }
}
