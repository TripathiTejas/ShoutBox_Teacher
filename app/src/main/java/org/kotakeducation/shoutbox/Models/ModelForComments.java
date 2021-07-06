package org.kotakeducation.shoutbox.Models;

public class ModelForComments {
    String Username,Comment;

    public ModelForComments(String Username,String Comment){
        this.Username=Username;
        this.Comment=Comment;
    }

    public String getUsername() {
        return Username;
    }

    public String getComment() {
        return Comment;
    }
}
