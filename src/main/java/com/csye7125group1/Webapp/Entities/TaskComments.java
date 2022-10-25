package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="taskcomments")
@Table(name="taskcomments")
public class TaskComments {

    @Id
    private String commentid;
    private String reftaskid;
    private String comment;
    private LocalDateTime comment_created;
    private LocalDateTime comment_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask;

}
