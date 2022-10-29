package com.csye7125group1.Webapp.DataClasses;

import java.time.LocalDateTime;

public class CreateReminder {

    private String taskname;
    private LocalDateTime reminder;

    public CreateReminder(String taskname, LocalDateTime reminder) {
        this.taskname = taskname;
        this.reminder = reminder;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public LocalDateTime getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }
}
