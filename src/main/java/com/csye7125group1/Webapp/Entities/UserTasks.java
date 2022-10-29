package com.csye7125group1.Webapp.Entities;

import com.csye7125group1.Webapp.DataClasses.CreateTask;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name="usertasks")
@Table(name="usertasks")
public class UserTasks {


    @Id
    private String taskid;
    private String summary;
    private LocalDate duedate;
    private String task;
    private String priority;
    private LocalDateTime task_created;
    private LocalDateTime task_updated;
    private String state;

    @OneToMany(mappedBy = "usertask_tag", fetch = FetchType.LAZY)
    private List<TaskTags> tasktags = new ArrayList<>();

    @OneToMany(mappedBy = "usertask_reminder", fetch = FetchType.LAZY)
    private List<TaskReminders> taskreminders = new ArrayList<>();

    @OneToMany(mappedBy = "usertask_comment", fetch = FetchType.LAZY)
    private List<TaskComments> taskcomments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reflist", referencedColumnName = "listid")
    private UserLists userlist_task;

    public UserTasks() {    }

    public UserTasks(String taskid, String summary, LocalDate duedate, String task, LocalDateTime task_created, LocalDateTime task_updated, String state) {
        this.taskid = UUID.randomUUID().toString();
        this.summary = summary;
        this.duedate = duedate;
        this.task = task;
        this.task_created = task_created;
        this.task_updated = task_updated;
        this.state = state;
    }

    public UserTasks(CreateTask newtask){
        this.taskid = UUID.randomUUID().toString();
        this.summary = newtask.getSummary();
        this.task = newtask.getTask();
        this.duedate = newtask.getDuedate();
        this.priority = newtask.getPriority();
        this.task_created = LocalDateTime.now();
        this.task_updated = LocalDateTime.now();
        this.state = this.evalstate(duedate);

        }

    public void updatetime() {
        this.task_updated = LocalDateTime.now();
    }

    public String evalstate(LocalDate date){

        if ( LocalDate.now().isBefore(date)){
            return "TODO";
        }

        if ( LocalDate.now().isAfter(date)){
            return "OVERDUE";
        }

        return "DUE_TODAY";
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getTask_created() {
        return task_created;
    }

    public void setTask_created(LocalDateTime task_created) {
        this.task_created = task_created;
    }

    public LocalDateTime getTask_updated() {
        return task_updated;
    }

    public void setTask_updated(LocalDateTime task_updated) {
        this.task_updated = task_updated;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TaskTags> getTasktags() {
        return tasktags;
    }

    public void setTasktags(List<TaskTags> tasktags) {
        this.tasktags = tasktags;
    }

    public List<TaskReminders> getTaskreminders() {
        return taskreminders;
    }

    public void setTaskreminders(List<TaskReminders> taskreminders) {
        this.taskreminders = taskreminders;
    }

    public List<TaskComments> getTaskcomments() {
        return taskcomments;
    }

    public void setTaskcomments(List<TaskComments> taskcomments) {
        this.taskcomments = taskcomments;
    }

    public UserLists getUserlist() {
        return userlist_task;
    }

    public void setUserlist(UserLists userlist) {
        this.userlist_task = userlist;
    }


}

