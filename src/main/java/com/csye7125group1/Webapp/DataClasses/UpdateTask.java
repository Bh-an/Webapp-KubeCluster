package com.csye7125group1.Webapp.DataClasses;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UpdateTask {

    private String taskname;
    private String listname;
    private LocalDate duedate;

    public UpdateTask(String taskname, String listname, LocalDate duedate) {
        this.taskname = taskname;
        this.listname = listname;
        this.duedate = duedate;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }
}
