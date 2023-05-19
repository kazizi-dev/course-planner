package ca.project.model.bean;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "appName", "authorName"})
public class About {
    private String appName;
    private String authorName;

    public About(){
        appName = "Course Planner";
        authorName = "Kourosh Azizi";
    }

    public void setAppName(String appName){
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }
}
