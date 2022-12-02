package com.csye7125group1.Webapp.DataClasses;

import com.csye7125group1.Webapp.Entities.UserTasks;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "usertasks")
public class SearchTask {

    @Id
    private String taskid;

    @Field(type = FieldType.Text, name = "task")
    private String task;

    @Field(type = FieldType.Text, name = "summary")
    private String summary;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    public SearchTask() {
    }

    public SearchTask(String taskid, String task, String summary, String username) {
        this.taskid = taskid;
        this.task = task;
        this.summary = summary;
        this.username = username;
    }

    public SearchTask(UserTasks task, String uname){

        this.taskid = task.getTaskid();
        this.task = task.getTask();
        this.summary = task.getSummary();
        this.username = uname;

    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
