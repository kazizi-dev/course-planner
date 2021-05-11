package ca.cmpt213.as5.reader;

import ca.cmpt213.as5.exception.ResourceNotFoundException;
import ca.cmpt213.as5.output.Print;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    This class reads a provided CSV file from a relative
    path, and it will split each line of the CSV and separate
    any unnecessary information to store each line inside a
    list. It then allows the user to access the list of all lines.
 */

public class CsvReader {
    private static final String SPLIT_BY_COMMA = ",";
    private static final int FAILURE = -1;
    private String csvFilePath;
    private List<String> sentenceLinesList = new ArrayList();

    public CsvReader(String csvFilePath) throws ResourceNotFoundException, IOException {
        try{
            if(csvFilePath.endsWith(".csv")) {
                this.csvFilePath = csvFilePath;
                readFile(this.csvFilePath);
            }
            else{
                throw new ResourceNotFoundException("No .csv file found!");
            }
        }
        catch(ResourceNotFoundException e){
            System.exit(FAILURE);
        }
    }

    private void readFile(String csvPathFile) throws IOException, ResourceNotFoundException {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.csvFilePath));
            String sentenceLine;
            while ((sentenceLine = bufferedReader.readLine()) != null) {
                sentenceLine = Arrays.toString(sentenceLine.split(SPLIT_BY_COMMA));
                sentenceLinesList.add(getRidOfUselessStrings(sentenceLine));
            }
        }
        catch(IOException e){
            System.out.println("IOException occurred while reading the CSV file.");
            System.exit(FAILURE);
        }
    }

    public List<String> getSentenceList(){
        return sentenceLinesList;
    }

    // .............. Helper functions ................ //
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

    // 1. Debugging
    public void printCsvSentenceList(){
        for(String sentence : sentenceLinesList){
            System.out.println(sentence);
        }
        System.out.println("*** End of testing ***");
    }
}
