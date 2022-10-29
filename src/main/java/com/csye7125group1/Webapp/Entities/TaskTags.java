package com.csye7125group1.Webapp.Entities;

import com.csye7125group1.Webapp.DataClasses.CreateTag;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name="tasktags")
@Table(name="tasktags")
public class TaskTags {

    @Id
    private String tagid;
    private String tag;
    private LocalDateTime tag_created;
    private LocalDateTime tag_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask_tag;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "refuser", referencedColumnName = "userid")
    private AppUser appuser_tag;


    public TaskTags() {
    }

    public TaskTags(CreateTag newtag){
        this.tagid = UUID.randomUUID().toString();
        this.tag = newtag.getTag();
        this.tag_created = LocalDateTime.now();
        this.tag_updated = LocalDateTime.now();
    }

    public void updatetime() {
        this.tag_updated = LocalDateTime.now();
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getTag_created() {
        return tag_created;
    }

    public void setTag_created(LocalDateTime tag_created) {
        this.tag_created = tag_created;
    }

    public LocalDateTime getTag_updated() {
        return tag_updated;
    }

    public void setTag_updated(LocalDateTime tag_updated) {
        this.tag_updated = tag_updated;
    }

    public UserTasks getUsertask() {
        return usertask_tag;
    }

    public void setUsertask(UserTasks usertask) {
        this.usertask_tag = usertask;
    }

    public AppUser getAppuser() {
        return appuser_tag;
    }

    public void setAppuser(AppUser appuser) {
        this.appuser_tag = appuser;
    }
}
