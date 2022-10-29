package com.csye7125group1.Webapp.DataClasses;

public class CreateComment {

    private String taskname;
    private String comment;

    public CreateComment(String taskname, String comment) {
        this.taskname = taskname;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }
}
