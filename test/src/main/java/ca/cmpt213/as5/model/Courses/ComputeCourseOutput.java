package ca.cmpt213.as5.model.Courses;
import ca.cmpt213.as5.model.CatalogNumber;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*
    This class will calculate the Course field for
    the output curl commands used by the user.
 */
@JsonPropertyOrder({ "courseId", "catalogNumber"})
public class ComputeCourseOutput implements Comparable<ComputeCourseOutput>{
    private Integer courseId;
    private String catalogNumber;

    public ComputeCourseOutput(){

    }

    public ComputeCourseOutput(CoursePlanner courses){
        this.courseId = courses.getCourseId();
        this.catalogNumber = courses.getCatalog().getCatalogNum();
//        this.department = courses.getDepartment();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(CatalogNumber catalogNumber) {
        this.catalogNumber = catalogNumber.getCatalogNum();
    }

    @Override
    public int compareTo(ComputeCourseOutput other) {
        return catalogNumber.compareTo(other.catalogNumber);
    }
}
