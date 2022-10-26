package com.csye7125group1.Webapp.DataClasses;

import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.TaskReminders;

import java.time.LocalDate;
import java.util.List;

public class CreateTask {

    private String listname;
    private String summary;
    private String task;
    private LocalDate duedate;
    private String priority;

    public CreateTask(String summary, String task, LocalDate duedate, String priority) {
        this.summary = summary;
        this.task = task;
        this.duedate = duedate;
        this.priority = priority;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
