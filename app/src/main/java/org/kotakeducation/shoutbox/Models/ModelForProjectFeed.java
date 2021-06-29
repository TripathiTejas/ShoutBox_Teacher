package org.kotakeducation.shoutbox.Models;

public class ModelForProjectFeed {

    String Title,Username,Date,UserId,ProjectID;

    public ModelForProjectFeed(String Title,String Username,String Date,String UserID,String ProjectID){
        this.Title=Title;
        this.Username=Username;
        this.Date=Date;
        this.UserId=UserID;
        this.ProjectID=ProjectID;
    }

    public String getTitle(){return Title;}
    public String getUsername(){return Username;}
    public String getDate(){return Date;}
    public  String getUserId(){return UserId;}
    public  String getProjectID(){return ProjectID;}

}
