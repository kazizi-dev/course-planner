package ca.project.service;


import ca.project.exception.OfferingNotFoundException;
import ca.project.model.bean.Department;
import ca.project.model.bean.Graph;
import ca.project.model.bean.Offering;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OfferingService {
    private List<Graph> graphs;

    public OfferingService(){}

    public Offering getOfferingById(List<Offering> offerings, int offeringId) throws OfferingNotFoundException {
        for(Offering offering : offerings){
            if(offering.getCourseOfferingId() == offeringId){
                return offering;
            }
        }
        throw new OfferingNotFoundException();
    }

    public List<Graph> getGraphs(Department department, int firstSemester, int lastSemester){
        graphs = new LinkedList<>();
        for (int i = firstSemester; i < lastSemester; i++) {
            if (isValidSemesterCode(i)) {
                Graph graph = new Graph();
                graph.updateTotalCoursesTaken(department, i);
                graphs.add(graph);
            }
        }

        return graphs;
    }

    private boolean isValidSemesterCode(int code) {
        // semester codes are the last digit of course codes
        int semesterCode = code % 10;
        return semesterCode == 1
                || semesterCode == 4
                || semesterCode == 7;
    }
}
