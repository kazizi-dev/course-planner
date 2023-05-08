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

public class Reader {
    private static final String SPLIT_BY_COMMA = ",";
    private static final int FAILURE = -1;
    private String csvFilePath;
    private List<String> sentenceLines = new ArrayList();

    public Reader(String filePath) throws ResourceNotFoundException, IOException {
        if (!filePath.endsWith(".csv")) {
            throw new ResourceNotFoundException("No .csv file found!");
        }
        this.csvFilePath = filePath;
        readFile();
    }

    public List<String> getSentenceList(){
        return sentenceLines;
    }

    // .............. Helper functions ................ //
    private void readFile() throws IOException, ResourceNotFoundException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath))) {
            String sentenceLine;
            while ((sentenceLine = bufferedReader.readLine()) != null) {
                sentenceLine = Arrays.toString(sentenceLine.split(SPLIT_BY_COMMA));
                String cleanLine = getRidOfUselessStrings(sentenceLine);
                sentenceLines.add(cleanLine);
            }
        }
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
    public void printCsvSentenceList(){
        for(String sentence : sentenceLines){
            System.out.println(sentence);
        }
        System.out.println("*** End of testing ***");
    }
}
