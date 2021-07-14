package org.kotakeducation.shoutbox.Models;

public class ModelForProjectFeed {

    String Title,Username,Date,UserId,ProjectID;

    EnquiryProjectModel model;

    public ModelForProjectFeed(String Title,String Username,String Date,String UserID,String ProjectID){
        this.Title=Title;
        this.Username=Username;
        this.Date=Date;
        this.UserId=UserID;
        this.ProjectID=ProjectID;
    }

    public ModelForProjectFeed(String title, String username, String date, String userId, String projectID, EnquiryProjectModel model) {
        Title = title;
        Username = username;
        Date = date;
        UserId = userId;
        ProjectID = projectID;
        this.model = model;
    }

    public ModelForProjectFeed() {
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public EnquiryProjectModel getModel() {
        return model;
    }

    public void setModel(EnquiryProjectModel model) {
        this.model = model;
    }

    public String getTitle(){return Title;}
    public String getUsername(){return Username;}
    public String getDate(){return Date;}
    public  String getUserId(){return UserId;}
    public  String getProjectID(){return ProjectID;}

}
