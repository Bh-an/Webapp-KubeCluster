package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="taskreminders")

public class TaskReminders {

    @Id
    private String reminderid;
    private LocalDateTime reminder;
    private LocalDateTime reminder_created;
    private LocalDateTime reminder_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask_reminder;

    public TaskReminders() {
    }

    public String getReminderid() {
        return reminderid;
    }

    public void setReminderid(String reminderid) {
        this.reminderid = reminderid;
    }

    public LocalDateTime getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateTime reminder) {
        this.reminder = reminder;
    }

    public LocalDateTime getReminder_created() {
        return reminder_created;
    }

    public void setReminder_created(LocalDateTime reminder_created) {
        this.reminder_created = reminder_created;
    }

    public LocalDateTime getReminder_updated() {
        return reminder_updated;
    }

    public void setReminder_updated(LocalDateTime reminder_updated) {
        this.reminder_updated = reminder_updated;
    }

    public UserTasks getUsertask() {
        return usertask_reminder;
    }

    public void setUsertask(UserTasks usertask) {
        this.usertask_reminder = usertask;
    }
}
