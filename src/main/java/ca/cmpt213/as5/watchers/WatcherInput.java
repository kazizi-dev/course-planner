package ca.cmpt213.as5.watchers;

/*
    This class receives inputs from the user to build a watcher for a
    course.
 */
public class WatcherInput {
    private int deptId;
    private int courseId;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
