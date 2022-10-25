package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="taskreminders")
@Table(name="taskreminders")

public class TaskReminders {

    @Id
    private String reminderid;
    private String reftaskid;
    private LocalDateTime reminder;
    private LocalDateTime reminder_created;
    private LocalDateTime reminder_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask;

}
