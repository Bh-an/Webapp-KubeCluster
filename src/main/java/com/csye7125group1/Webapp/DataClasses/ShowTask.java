package com.csye7125group1.Webapp.DataClasses;

import com.csye7125group1.Webapp.Entities.TaskComments;
import com.csye7125group1.Webapp.Entities.TaskReminders;
import com.csye7125group1.Webapp.Entities.TaskTags;
import com.csye7125group1.Webapp.Entities.UserTasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowTask {


    private String taskid;
    private String listname;
    private String summary;
    private LocalDate duedate;
    private String task;
    private String priority;
    private LocalDateTime task_created;
    private LocalDateTime task_updated;
    private String state;
    private List<String> comments = new ArrayList<>();
    private List<LocalDateTime> reminders = new ArrayList<>();
    private List<String> tags = new ArrayList<>();


    public ShowTask() {
    }

    public ShowTask(UserTasks task){

        this.taskid = task.getTaskid();
        this.summary = task.getSummary();
        this.task = task.getTask();
        this.duedate = task.getDuedate();
        this.priority = task.getPriority();
        this.task_created = task.getTask_created();
        this.task_updated = task.getTask_updated();
        this.state = task.getState();
        for (TaskComments comment : task.getTaskcomments()){
            this.comments.add(comment.getComment());
        }
        for (TaskReminders reminder : task.getTaskreminders()){
            this.reminders.add(reminder.getReminder());
        }
        for (TaskTags tag : task.getTasktags()){
            this.tags.add(tag.getTag());
        }
        this.listname = task.getUserlist_task().getListname();
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

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<LocalDateTime> getReminders() {
        return reminders;
    }

    public void setReminders(List<LocalDateTime> reminders) {
        this.reminders = reminders;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname = listname;
    }
}
