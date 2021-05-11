package ca.cmpt213.as5.watchers;

import ca.cmpt213.as5.model.Courses.ComputeCourseOutput;
import ca.cmpt213.as5.model.Courses.CoursePlanner;
import ca.cmpt213.as5.model.Department;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/*
    This class helps the controller to assign the right fields to a watcher
    for the web ui.
 */
public class Watcher  implements IObserver{
    private static AtomicLong nextId = new AtomicLong();
    private long id;
    private Department department;
    private ComputeCourseOutput course;
    private List<String> events = new ArrayList<>();

    public Watcher(long id){
        this.id = id;
    }

    public Watcher(CoursePlanner planner) {
        department = planner.getDepartment();
        course = new ComputeCourseOutput(planner);
        id = nextId.getAndIncrement();
    }

    public long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public ComputeCourseOutput getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Watcher) {
            Watcher other = (Watcher) obj;
            return id == other.id;
        }
        return false;
    }
    @Override
    public void update(CoursePlanner planner) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("PDT"));

        Date dateAdded = calendar.getTime();

        String event = "" + dateAdded + ": Added section " + planner.getComponent().getComponentCode()
                + " with enrollment (" + planner.getEnrolmentTotalObj().getEnrolmentTotalNum() + " / "
                + planner.getEnrolMentCapObj().getEnrolmentCap()
                + ") to offering " + planner.term() + " " + planner.year();
        events.add(event);
    }
}
