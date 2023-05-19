package ca.project.model.bean;

import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
public class Instructor {
    private String fullName;
    private Set<String> names;        // use hashset to avoid duplicate names
    private static final String SPLIT_BY_COMMA = ",";
    private static final String STRING_SIGN = "\"";
    private static final String STRING_NULL = "nu";
    private static final String EMPTY_STRING = "";

    public Instructor(){
        names = new HashSet<>();
    }

    public void initializeInstructors(String[] separatedWords, int start, int end){
        names = new HashSet<>();

        if(start != end){
            while(start <= end){
                if(start == end){
                    separatedWords[start] = separatedWords[start].substring(0,separatedWords[start].length() - 1);
                }
                setFullName(separatedWords[start].trim());
                start++;
            }
        }
        else{
            separatedWords[start] = separatedWords[start].substring(0,separatedWords[start].length() - 2);
            this.fullName = separatedWords[start].trim();
            setFullName(fullName);
        }
    }

    public void setFullName(String newName){
        if(isMultipleInstructors(newName)){
            String[] profNames = newName.split(SPLIT_BY_COMMA);

            for(String name : profNames){
                if(name.equalsIgnoreCase(STRING_NULL)){
                    name = ("TBD");
                    names.add(name);
                    this.fullName = name;
                }
                else {
                    this.fullName = name;
                    names.add(fullName);
                }
            }
        }
        else{
            if(newName.equalsIgnoreCase(STRING_NULL)){
                this.fullName = "TBD";
//                names.add(fullName);
            }
            else {
                this.fullName = newName;
//                names.add(fullName);
            }
        }
    }

    private Boolean isMultipleInstructors(String name){
        return name.contains(SPLIT_BY_COMMA);
    }

    private Boolean isMultipleInstructors(Set<String> names){
        return names.contains(SPLIT_BY_COMMA);
    }
}
