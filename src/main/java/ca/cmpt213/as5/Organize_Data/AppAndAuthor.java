package ca.cmpt213.as5.Organize_Data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*
    This class shows the application author and name. It
    communicates with the web ui.
 */
@JsonPropertyOrder({ "appName", "authorName"})
public class AppAndAuthor {
    private String appName;
    private String authorName;

    public AppAndAuthor(){
        appName = "Kourosh and Quince Awesome App";
        authorName = "Kourosh Azizi and Quince Bielka";
    }

    public AppAndAuthor(String appName, String authorName){
        this.appName = appName;
        this.authorName = authorName;
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
