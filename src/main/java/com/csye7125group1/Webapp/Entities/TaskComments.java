package com.csye7125group1.Webapp.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="taskcomments")
@Table(name="taskcomments")
public class TaskComments {

    @Id
    private String commentid;
    private String comment;
    private LocalDateTime comment_created;
    private LocalDateTime comment_updated;

    @ManyToOne(fetch = FetchType.LAZY, optional =  false)
    @JoinColumn(name = "reftask", referencedColumnName = "taskid")
    private UserTasks usertask_comment;

    public TaskComments() {
    }

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getComment_created() {
        return comment_created;
    }

    public void setComment_created(LocalDateTime comment_created) {
        this.comment_created = comment_created;
    }

    public LocalDateTime getComment_updated() {
        return comment_updated;
    }

    public void setComment_updated(LocalDateTime comment_updated) {
        this.comment_updated = comment_updated;
    }

    public UserTasks getUsertask() {
        return usertask_comment;
    }

    public void setUsertask(UserTasks usertask) {
        this.usertask_comment = usertask;
    }


}
