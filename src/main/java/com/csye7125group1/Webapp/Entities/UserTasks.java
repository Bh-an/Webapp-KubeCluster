package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="usertasks")
@Table(name="usertasks")
public class UserTasks {

    @Id
    private String taskid;
    private String summary;
    private LocalDateTime duedate;
    private String task;
    private enum priority {High, Medium, Low};
    private LocalDateTime task_created;
    private LocalDateTime task_updated;

    @OneToMany(mappedBy = "usertask", fetch = FetchType.EAGER)
    private List<TaskTags> tasktags = new ArrayList<>();

    @OneToMany(mappedBy = "usertask", fetch = FetchType.EAGER)
    private List<TaskReminders> taskreminders = new ArrayList<>();

    @OneToMany(mappedBy = "usertask", fetch = FetchType.EAGER)
    private List<TaskComments> taskcomments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reflist", referencedColumnName = "listid")
    private UserLists userlist;



}
