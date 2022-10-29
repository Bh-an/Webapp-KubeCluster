package com.csye7125group1.Webapp.DataClasses;

public class CreateTag {

    private String tag;
    private String task;

    public CreateTag(String tag, String task) {
        this.tag = tag;
        this.task = task;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}


