package ca.cmpt213.as5.watchers;

import ca.cmpt213.as5.model.Courses.CoursePlanner;

/*
    An observer that is used in the observer
    pattern.
 */
public interface IObserver {
    void update(CoursePlanner planner);
}
